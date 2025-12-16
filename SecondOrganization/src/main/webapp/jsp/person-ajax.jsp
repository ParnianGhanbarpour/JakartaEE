<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>مدیریت پرسنل - AJAX</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.rtl.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2><i class="bi bi-people"></i> مدیریت پرسنل با AJAX</h2>

    <div class="row mb-4">
        <div class="col-md-6">
            <input type="text" id="searchInput" class="form-control"
                   placeholder="جستجوی نام، نام خانوادگی یا کد ملی...">
        </div>
        <div class="col-md-6">
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addModal">
                <i class="bi bi-plus-circle"></i> افزودن پرسنل
            </button>
        </div>
    </div>

    <div id="loading" class="text-center" style="display:none;">
        <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">در حال بارگذاری...</span>
        </div>
    </div>

    <div id="personsTable"></div>

    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div id="messageToast" class="toast" role="alert">
            <div class="toast-header">
                <strong class="me-auto" id="toastTitle">پیام</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
            </div>
            <div class="toast-body" id="toastMessage"></div>
        </div>
    </div>
</div>

<div class="modal fade" id="addModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">افزودن پرسنل جدید</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form id="addForm">
                    <div class="mb-3">
                        <label class="form-label">نام</label>
                        <input type="text" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">نام خانوادگی</label>
                        <input type="text" name="family" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">کد ملی</label>
                        <input type="text" name="nationalCode" class="form-control"
                               pattern="[0-9]{10}" maxlength="10" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">جنسیت</label>
                        <select name="genderValue" class="form-select" required>
                            <option value="male">مرد</option>
                            <option value="female">زن</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">لغو</button>
                <button type="button" class="btn btn-primary" onclick="savePerson()">
                    <i class="bi bi-save"></i> ذخیره
                </button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    async function loadPersons(searchTerm = '') {
        showLoading(true);

        try {
            let url = '/api/v1/persons';
            if (searchTerm) {
                url += `?search=${encodeURIComponent(searchTerm)}`;
            }

            const response = await fetch(url);

            if (!response.ok) {
                throw new Error('Failed to fetch persons');
            }

            const result = await response.json();
            const persons = result.data || result;
            displayPersons(persons);

        } catch (error) {
            console.error('Error:', error);
            showToast('خطا', 'خطا در بارگذاری اطلاعات', 'error');
        } finally {
            showLoading(false);
        }
    }

    function displayPersons(persons) {
        const container = document.getElementById('personsTable');

        if (!persons || persons.length === 0) {
            container.innerHTML = '<div class="alert alert-info">هیچ پرسنلی یافت نشد</div>';
            return;
        }

        let html = `
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>نام</th>
                    <th>نام خانوادگی</th>
                    <th>کد ملی</th>
                    <th>جنسیت</th>
                    <th>عملیات</th>
                </tr>
            </thead>
            <tbody>
    `;

        persons.forEach(person => {
            html += `
            <tr>
                <td>${person.name}</td>
                <td>${person.family}</td>
                <td>${person.nationalCode}</td>
                <td>${person.genderTitle}</td>
                <td>
                    <button class="btn btn-sm btn-danger"
                            onclick="deletePerson(${person.id}, '${person.name} ${person.family}')">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            </tr>
        `;
        });

        html += '</tbody></table>';
        container.innerHTML = html;
    }

    async function savePerson() {
        const form = document.getElementById('addForm');

        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        const formData = new FormData(form);
        const data = Object.fromEntries(formData.entries());

        try {
            const response = await fetch('/api/v1/persons', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'خطا در ذخیره');
            }

            showToast('موفق', 'پرسنل با موفقیت ذخیره شد', 'success');

            bootstrap.Modal.getInstance(document.getElementById('addModal')).hide();
            form.reset();
            loadPersons();

        } catch (error) {
            console.error('Error:', error);
            showToast('خطا', error.message, 'error');
        }
    }

    async function deletePerson(id, name) {
        if (!confirm(`آیا از حذف ${name} مطمئن هستید؟`)) {
            return;
        }

        try {
            const response = await fetch(`/api/v1/persons/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                throw new Error('خطا در حذف');
            }

            showToast('موفق', 'پرسنل با موفقیت حذف شد', 'success');
            loadPersons();

        } catch (error) {
            console.error('Error:', error);
            showToast('خطا', error.message, 'error');
        }
    }

    let searchTimeout;
    document.getElementById('searchInput').addEventListener('input', function(e) {
        clearTimeout(searchTimeout);
        const searchTerm = e.target.value;

        searchTimeout = setTimeout(() => {
            loadPersons(searchTerm);
        }, 500);
    });


    function showLoading(show) {
        document.getElementById('loading').style.display = show ? 'block' : 'none';
    }

    function showToast(title, message, type) {
        const toastEl = document.getElementById('messageToast');
        document.getElementById('toastTitle').textContent = title;
        document.getElementById('toastMessage').textContent = message;

        toastEl.className = 'toast';
        if (type === 'error') {
            toastEl.classList.add('bg-danger', 'text-white');
        } else {
            toastEl.classList.add('bg-success', 'text-white');
        }

        new bootstrap.Toast(toastEl).show();
    }

    document.addEventListener('DOMContentLoaded', function() {
        loadPersons();
    });
</script>
</body>
</html>
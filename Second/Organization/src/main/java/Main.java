//import com.organization.model.entity.Organization;
//import com.organization.model.service.GenericService;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//
//public class Main {
//    public static void main(String[] args) {
//        EntityManagerFactory factory= Persistence.createEntityManagerFactory("organization");
//        EntityManager entityManager=factory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        transaction.begin();
//
//
//        transaction.commit();
//        entityManager.close();
////        try {
////            GenericService<Organization, Integer> orgService = new GenericService<>();
////            Organization org = Organization.builder()
////                    .name("TestOrg")
////                    .organizationType("Private")
////                    .build();
////
////
////            orgService.save(org);
////
////            orgService.findAll(Organization.class).forEach(System.out::println);
////
////            Organization found = orgService.findById(1, Organization.class);
////            System.out.println("Found: " + found);
////
////            orgService.deleteById(1, Organization.class);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//}


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main{
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("organization");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}

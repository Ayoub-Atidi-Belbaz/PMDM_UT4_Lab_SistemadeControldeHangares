/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ayoub Atidi Belbaz
 */
package ayoub.sistemadecontroldehangares;


import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HagarControl {

    //Persistimos una nueva nave con transacción segura
    public void registrarNuevaNave(Nave nave) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(nave); // Método de Hibernate para guardar
            tx.commit();
            System.out.println(" Nave registrada con ID: " + nave.getId());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(" Error al registrar nave: " + e.getMessage());  
        }
    }

    // Modificamos el estado recuperando por ID (aprovechando el Dirty Checking)
    public void actualizarEstadoNave(int idNave, String nuevoEstado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Nave nave = session.get(Nave.class, idNave); // Recuperamos por ID
            if (nave != null) {
                nave.setEstadoSistema(nuevoEstado); // Modificamos en Java
                tx.commit(); // Dirty checking que lanza el update en PostgreSQL
                System.out.println("Estado actualizado a '" + nuevoEstado + "' para la nave ID: " + idNave);
            } else {
                tx.rollback();
                System.out.println(" No se encontró ninguna nave con ID: " + idNave);
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println(" Error al actualizar estado: " + e.getMessage());
        }
    }

    // Consulta HQL parametrizada
    public List<Nave> obtenerCazasPorModelo(String modeloBuscado) {
        List<Nave> resultados = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Consulta HQL apuntando a la clase 'Nave' y su atributo 'modelo'
            String hql = "FROM Nave n WHERE n.modelo = :modParam";
            Query<Nave> query = session.createQuery(hql, Nave.class);
            query.setParameter("modParam", modeloBuscado); // Nombre del parámetro HQL
            resultados = query.getResultList(); // Método para obtener la lista de resultados
        } catch (Exception e) {
            System.err.println("Error en consulta HQL: " + e.getMessage());
        }
        return resultados;
    }
}
package com.nagarro.image.dao;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.nagarro.image.model.FilesModel;
import com.nagarro.image.model.UserModel;

public class UserDAO {
	static Configuration configuration = new Configuration().addAnnotatedClass(UserModel.class)
			.addAnnotatedClass(FilesModel.class).configure();
	static SessionFactory sf = configuration.buildSessionFactory();

	static Configuration configuration2 = new Configuration().addAnnotatedClass(FilesModel.class).configure();
	static SessionFactory sf2 = configuration.buildSessionFactory();
	
	public void saveUser(UserModel user) {
		Transaction transaction = null;
		try (Session session = sf.openSession()) {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public static boolean validate(String userName, String password) {
		UserModel user = null;
		try (Session session = sf.openSession()) {
			user = new UserModel();
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserModel> criteria = builder.createQuery(UserModel.class);
			Root<UserModel> root = criteria.from(UserModel.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("user_name"), userName));
			user = session.createQuery(criteria).uniqueResult();
			if (user != null && user.getPassword().equals(password)) {
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public static void saveFile(FilesModel file) {
		try (Session session1 = sf2.openSession()) {
			session1.beginTransaction();
			session1.save(file);
			session1.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void deleteFile(FilesModel file) {
		Transaction transaction = null;
		try (Session session = sf.openSession()) {
			transaction = session.beginTransaction();

			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static UserModel getDetails(String username) {
		UserModel user = null;
		try (Session session = sf.openSession()) {
			user = new UserModel();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<UserModel> criteria = builder.createQuery(UserModel.class);
			Root<UserModel> root = criteria.from(UserModel.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("username"), username));
			user = session.createQuery(criteria).uniqueResult();

		} catch (Exception e) {
			System.out.println("Unable to get user Details");
		}
		return user;
	}

	public static double getImagesSize(String username) {
		double totalSize = 0;
		UserModel user = getDetails(username);
		Set<FilesModel> images = user.getImages();
		for (FilesModel image : images) {
			totalSize += image.getFileSize();
		}
		return totalSize;
	}
}

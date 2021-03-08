package com.nagarro.image.dao;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.nagarro.image.data.FilesModel;
import com.nagarro.image.data.UserModel;

import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO {
	static Configuration configuration = new Configuration().addAnnotatedClass(FilesModel.class)
			.addAnnotatedClass(UserModel.class).configure();
	static SessionFactory sf = configuration.buildSessionFactory();

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
		
		Transaction transaction = null;
		
		try (Session session = sf.openSession()) {
			transaction = session.beginTransaction();
			session.save(file);
			transaction.commit();
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
			criteria.where(builder.equal(root.get("user_name"), username));
			user = session.createQuery(criteria).uniqueResult();

		} catch (Exception e) {
			System.out.println("Unable to get user Details");
			e.printStackTrace();
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

	public static FilesModel getImage(String imageId) {
		FilesModel ir = null;
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<FilesModel> criteria = builder.createQuery(FilesModel.class);
			Root<FilesModel> root = criteria.from(FilesModel.class);
			criteria.select(root);
			criteria.where(builder.equal(root.get("fileid"), imageId));
			ir = session.createQuery(criteria).uniqueResult();
		} catch (Exception e) {
			System.out.println("Unable to get Image");
		}
		return ir;
	}

	public static void editImage(FilesModel image) {

		try (Session session = sf.openSession()) {
			session.beginTransaction();
			session.update(image);
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Unable to edit image");
		}

	}

	public static void deleteImage(String imageId) {

		try (Session session = sf.openSession()) {

			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaDelete<FilesModel> criteria = builder.createCriteriaDelete(FilesModel.class);
			Root<FilesModel> root = criteria.from(FilesModel.class);
			criteria.where(builder.equal(root.get("fileid"), imageId));
			session.createQuery(criteria).executeUpdate();
			session.getTransaction().commit();
		}

		catch (Exception e) {
			System.out.println("Unable to delete image");
		}

	}
}

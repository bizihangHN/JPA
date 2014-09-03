package com.whj.user;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.micmiu.hibernate.jpa.UserInfo;

/**
 * 单元测试
 * 
 * @author <a href="http://www.micmiu.com">Michael</a>
 * @time Create on 2013-9-20 下午11:23:32
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class JunitTest {
	AtomicInteger atom = new AtomicInteger();

	EntityManagerFactory emf = null;

	@Before
	public void before() {
		// 根据persistence.xml中配置创建EntityManagerFactory
		emf = Persistence.createEntityManagerFactory("myJPA");
	}

	@After
	public void after() {
		if (null != emf) {
			emf.close();
		}
	}

	@Test
	public void testCreate() {
		System.out.println(">>>> testCreate <<<<");
		int beforeCount = queryList();
		System.out.println(">>>> before create count = " + beforeCount);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		UserInfo user = generateRadomEntity();
		em.persist(user);
		em.getTransaction().commit();
		em.close();

		int afterCount = queryList();
		System.out.println(">>>> after create count = " + afterCount);
		Assert.assertEquals(beforeCount + 1, afterCount);
	}

	@Test
	public void testRead() {
		System.out.println(">>>> testRead <<<<");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		UserInfo user = em.find(UserInfo.class, 1);
		if (null != user) {
			Assert.assertEquals(1, (long) user.getId());
			System.out.println(">>>> find user = " + user);
		} else {
			System.out.println(">>>> find use not exit.");
		}
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * 更新操作- 用于托管状态的对象
	 */
	@Test
	public void testUpdate() {
		System.out.println(">>>> testUpdate <<<<");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		UserInfo user = em.find(UserInfo.class, 1);
		System.out.println(user);
		user.setEmail("micmiu.com@gmail.com");
		em.getTransaction().commit();
		em.close();

		UserInfo user2 = findUser(1);
		System.out.println(user2);
		Assert.assertEquals("micmiu.com@gmail.com", user2.getEmail());

	}

	/**
	 * 更新操作 - 用于游离(脱管)状态的对象
	 */
	@Test
	public void testMerge() {
		System.out.println(">>>> testMerge <<<<");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		UserInfo user = em.find(UserInfo.class, 1);
		System.out.println(user);
		// 将实体管理器中的所有实体变成了游离态(脱管)
		em.clear();

		user.setEmail("michael@micmiu.com");
		em.merge(user);
		em.getTransaction().commit();
		em.close();

		UserInfo user2 = findUser(1);
		System.out.println(user2);
		Assert.assertEquals("michael@micmiu.com", user2.getEmail());

	}

	@Test
	public void testDelete() {
		System.out.println(">>>> testDelete <<<<");
		int beforeCount = queryList();
		System.out.println(">>>> before delete count = " + beforeCount);

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		UserInfo user = em.find(UserInfo.class, 2);
		em.remove(user);
		em.getTransaction().commit();
		em.close();

		int afterCount = queryList();
		System.out.println(">>>> after delete count = " + afterCount);
		Assert.assertEquals(beforeCount - 1, afterCount);

	}

	private UserInfo findUser(Integer id) {
		EntityManager em = emf.createEntityManager();
		UserInfo user = em.find(UserInfo.class, id);
		em.close();
		return user;
	}

	private int queryList() {
		EntityManager em = emf.createEntityManager();
		List<UserInfo> list = (List<UserInfo>) em.createQuery(
				"select t from UserInfo t order by t.userName").getResultList();
		em.close();
		return list.size();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	private UserInfo generateRadomEntity() {
		UserInfo user = new UserInfo();
		String key = atom.addAndGet(1) + "";
		user.setUserName("michael-" + key);
		user.setEmail(key + "@micmiu.com");
		user.setBlogURL("micmiu.com");
		return user;
	}

}
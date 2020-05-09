package com.lujia.interview.study.arrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * �����಻��ȫ����
 * 
 * @author Romantic-Lei
 * @create 2020��5��9��
 */
public class ContainerNotSafeDemo {

	public static void main(String[] args) {

		Map<String, String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new ConcurrentHashMap<>();//new HashMap<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
				System.out.println(map);
			}, String.valueOf(i)).start();
		}

	}

	public static void setNotSafe() {
		// HashSet�ĵײ����HashMap����������HashSetʹ��addʱֻ��һ��ֵ��ԭ���ǣ�
		// ���Ǵ洢�����ֵ��HashMap��key��value��һ���������������ǹ��ġ�
		Set<String> set = new CopyOnWriteArraySet<>();// Collections.synchronizedSet(new HashSet<>()); //new
														// HashSet<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				set.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(set);
			}, String.valueOf(i)).start();
		}
	}

	public static void listNotSafe() {
		// List<String> list = Arrays.asList("a", "b", "c");
		// list.forEach(System.out::println);

		// Collections.synchronizedList(new ArrayList<>()); //new Vector<>();//new
		// ArrayList<>();
		List<String> list = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 30; i++) {
			new Thread(() -> {
				list.add(UUID.randomUUID().toString().substring(0, 8));
				System.out.println(list);
			}, String.valueOf(i)).start();

			/**
			 * 1.�������� java.util.ConcurrentModificationException
			 * 
			 * 2.����ԭ�� ���������޸ĵ��·����쳣
			 * 
			 * 3.������� 3.1 ʹ��new Vector<>(); 3.2 ʹ��Collections.synchronizedList(new
			 * ArrayList<>()); 3.3ʹ��new CopyOnWriteArrayList<>();
			 * CopyOnWriteArrayList��дʱ���ƣ���Ҫ��һ�ֶ�д�����˼��
			 * дʱ���ƣ�CopyOnWrite������дʱ���Ƶ���������һ�����������Ԫ�ص�ʱ�򣬲�ֱ������ǰ����Object[]��ӣ�
			 * �����Ƚ�Object[]����copy�����Ƴ�һ���µ�����object[] newElements��Ȼ���µ�����Object[]
			 * newElements�����ԭʼ���ݣ� ���Ԫ������ڽ�ԭ����������ָ���µ�����
			 * setArray(newElements)���������ĺô��ǿ��Զ�copyOnWrite�������в����Ķȣ�
			 * ������Ҫ��������Ϊ��ǰ��������Ҫ����κ�Ԫ�ء�����CopyOnWrite����Ҳ��һ�ֶ�д�����˼�룬����д��ͬ������
			 * ����д��ʱ�򣬰�ArrayList����һ��������Ȼ���ֵ��д��ȥ����֪ͨ�������̣߳�ArrayList������ָ�����ݺ�ĵ�ַ
			 * 
			 * 
			 * 4.�Ż�����
			 */

		}
	}
}

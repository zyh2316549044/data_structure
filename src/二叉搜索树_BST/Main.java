package 二叉搜索树_BST;

import 二叉搜索树_BST.file.Files;
import 二叉搜索树_BST.printer.BinaryTreeInfo;
import 二叉搜索树_BST.printer.BinaryTrees;

import java.util.Comparator;


public class Main {
	
	private static class PersonComparator implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e1.getAge() - e2.getAge();
		}
	}
	
	private static class PersonComparator2 implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e2.getAge() - e1.getAge();
		}
	}

	public static void main(String[] args) {
		Integer data[] = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 10; i++) {
		    //添加随机的100个数(Math.random() * 100)
			bst.add((int)(Math.random() * 100));
		}

	/*	//传入到文件中
		String str= BinaryTrees.printString(bst);
        Files.writeToFile("E:/1.txt",str,true);*/

        BinaryTrees.println(bst);
		System.out.println(bst.isCopmlete());
	}
	}

	



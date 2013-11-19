package algorithm.tree.binary.avl;

import algorithm.tree.binary.BinaryTreeTraverse;
import algorithm.tree.binary.FetchAndRemoveRightMostNode;
import algorithm.tree.binary.FetchAndRemoveRightMostNode.Bean;
import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BSearchTree;
import andy.util.Log;

public class AVLTree<E extends Comparable<E>> implements BSearchTree<E> {
	private AvlNode<E> root = null;

	public AVLTree() {
	}

	@Override
	public BSearchNode<E> getRoot() {
		return root;
	}

	@Override
	public void insert(E v) {
		if (root == null) {
			root = new AvlNode<E>(v);
			return;
		}
		// Insert
		AvlNode<E> cursor = root;
		AvlNode<E> newAdded = null;
		while (cursor != null) {
			int r = cursor.getValue().compareTo(v);
			if (r == 0) {
				return;
			} else if (r > 0) {
				if (cursor.getLeft() == null) {
					newAdded = new AvlNode<E>(v);
					cursor.setLeft(newAdded);
					newAdded.setParent(cursor);
					break;
				} else {
					cursor = (AvlNode<E>) cursor.getLeft();
				}
			} else {
				if (cursor.getRight() == null) {
					newAdded = new AvlNode<E>(v);
					cursor.setRight(newAdded);
					newAdded.setParent(cursor);
					break;
				} else {
					cursor = (AvlNode<E>) cursor.getRight();
				}
			}
		}
		// update height and check whether need to balance
		newAdded.setHeight(1);
		AvlNode<E> child = (AvlNode<E>) newAdded.getParent();
		child.updateHeight();
		AvlNode<E> parent = (AvlNode<E>) child.getParent();
		boolean downIsLeft = (child.getLeft() == newAdded);
		if(parent==null){
		    return;
		}
		boolean upIsLeft = (parent.getLeft() == child);
		while (parent != null) {
			if (parent.isBalanced()) {
				int oh = parent.getHeight();
				parent.updateHeight();
				if (oh == parent.getHeight()) {
					// stop updating height, because parent'height is not
					// changed
					return;
				} else {
					downIsLeft = upIsLeft;
					child = parent;
					parent = (AvlNode<E>) parent.getParent();
					upIsLeft = parent!=null?(parent.getLeft() == child):false;
				}
			} else {
				break;
			}
		}
		if (parent == null) {
			return;
		}

		rotate(parent, upIsLeft, downIsLeft);
	}

	@Override
	public void remove(E v) {
		// remove target node and complement with a selected node
		AvlNode<E> cursor = root;
		AvlNode<E> parent = null;
		AvlNode<E> update_start_node = null;
		while (cursor != null) {
			int r = cursor.getValue().compareTo(v);
			if (r == 0) {
				AvlNode<E> replace = null;
				if (cursor.getLeft() == null) {
					replace = (AvlNode<E>) cursor.getRight();

					update_start_node = parent;
				} else if (cursor.getRight() == null) {
					replace = (AvlNode<E>) cursor.getLeft();

					update_start_node = parent;
				} else {
					Bean res = FetchAndRemoveRightMostNode.doit(cursor
							.getLeft());
					replace = (AvlNode<E>) res.biggest;
					replace.setLeft(res.left_tree);
					replace.setRight(cursor.getRight());

					if (res.biggest_origin_father != null) {
						update_start_node = (AvlNode<E>) res.biggest_origin_father;
					} else {
						update_start_node = replace;
					}
				}
				if (parent == null) {// root node is the deleted node
					root = replace;
				} else {
					if (parent.getLeft() == cursor) {
						parent.setLeft(replace);
					} else {
						parent.setRight(replace);
					}
				}
				break;
			} else if (r > 0) {
				parent = cursor;
				cursor = (AvlNode<E>) cursor.getLeft();
			} else {
				parent = cursor;
				cursor = (AvlNode<E>) cursor.getRight();
			}
		}
		if (update_start_node == null) {
			return;
		}

		// update height, and check whether need to rotate
		cursor = update_start_node;
		while (cursor != null) {
			while (cursor != null) {
				if (cursor.isBalanced()) {
					int oh = cursor.getHeight();
					cursor.updateHeight();
					if (cursor.getHeight() == oh) {
						return;// don't need to update any more
					} else {
						cursor = (AvlNode<E>) cursor.getParent();
					}
				} else {
					// not balanced, do rotation
					break;
				}
			}
			if (cursor == null) {
				return;
			}
			boolean upIsLeft = cursor.isLeftLonger();
			AvlNode<E> child = null;
			if (upIsLeft) {
				child = (AvlNode<E>) cursor.getLeft();
			} else {
				child = (AvlNode<E>) cursor.getRight();
			}
			boolean downIsLeft = child.isLeftLonger();
			int oh = cursor.getHeight();
			cursor = rotate(cursor, upIsLeft, downIsLeft);
			if (oh == cursor.getHeight()) {
				return;
			} else {
				cursor = (AvlNode<E>) cursor.getParent();
			}
		}
	}

	private AvlNode<E> rotate(AvlNode<E> parent, boolean upIsLeft,
			boolean downIsLeft) {
		// balance the tree
		// Don't forget to link the rotated sub tree with its parent
		AvlNode<E> father = (AvlNode<E>) parent.getParent();
		AvlNode<E> replaceForParent = null;
		boolean isLeft = true;
		if (father != null) {
			isLeft = (father.getLeft() == parent);
		}
		AvlNode<E> child = null;
		if (upIsLeft) {
			child = (AvlNode<E>) parent.getLeft();
		} else {
			child = (AvlNode<E>) parent.getRight();
		}
		if (upIsLeft && downIsLeft) {// left,left
			AvlNode<E> middle = (AvlNode<E>) child.getRight();

			child.setRight(parent);
			child.setParent(null);

			parent.setLeft(middle);
			parent.setParent(child);

			//Notice the order of updating nodes' height. must update the lower level node first.
            //Here, "parent" is the lower node
			parent.updateHeight();
			child.updateHeight();

			replaceForParent = child;
		} else if (!upIsLeft && !downIsLeft) {// right,right
			AvlNode<E> middle = (AvlNode<E>) child.getLeft();

			child.setLeft(parent);
			child.setParent(null);

			parent.setRight(middle);
			parent.setParent(child);

			//Notice the order of updating nodes' height. must update the lower level node first.
			//Here, "parent" is the lower node
			parent.updateHeight();
			child.updateHeight();

			replaceForParent = child;
		} else if (upIsLeft && !downIsLeft) {// left,right
			AvlNode<E> targetRoot = (AvlNode<E>) child.getRight();
			AvlNode<E> middle_left = (AvlNode<E>) targetRoot.getLeft();
			AvlNode<E> middle_right = (AvlNode<E>) targetRoot.getRight();

			child.setRight(middle_left);
			child.setParent(targetRoot);

			parent.setLeft(middle_right);
			parent.setParent(targetRoot);

			targetRoot.setLeft(child);
			targetRoot.setRight(parent);

			//Notice the order of updating nodes' height. must update the lower level node first.
            //Here, "child" and "parent" are the lower nodes
			child.updateHeight();
			parent.updateHeight();
			targetRoot.updateHeight();
			

			replaceForParent = targetRoot;
		} else {// right,left
			AvlNode<E> targetRoot = (AvlNode<E>) child.getLeft();
			AvlNode<E> middle_left = (AvlNode<E>) targetRoot.getLeft();
			AvlNode<E> middle_right = (AvlNode<E>) targetRoot.getRight();

			child.setLeft(middle_right);
			child.setParent(targetRoot);

			parent.setRight(middle_left);
			parent.setParent(targetRoot);

			targetRoot.setLeft(parent);
			targetRoot.setRight(child);

			//Notice the order of updating nodes' height. must update the lower level node first.
            //Here, "child" and "parent" are the lower nodes
			child.updateHeight();
			parent.updateHeight();
			targetRoot.updateHeight();

			replaceForParent = targetRoot;
		}

		// link the rotated tree with father
		if (father == null) {
			root = replaceForParent;
			replaceForParent.setParent(null);
		} else if (isLeft) {
			father.setLeft(replaceForParent);
			replaceForParent.setParent(father);
		} else {
			father.setRight(replaceForParent);
			replaceForParent.setParent(father);
		}
		return replaceForParent;
	}

	@Override
	public BSearchNode<E> search(E v) {
		BSearchNode<E> cursor = root;
		while (cursor != null) {
			int r = cursor.getValue().compareTo(v);
			if (r == 0) {
				break;
			} else if (r > 0) {
				cursor = (BSearchNode<E>) cursor.getLeft();
			} else {
				cursor = (BSearchNode<E>) cursor.getRight();
			}
		}
		return cursor;
	}
	public static void main(String[]args){
	    testInsert_Right_Right();
	    testInsert_Right_Left();
	    testInsert_Left_Left();
	    testInsert_Left_Right();
	    testDelete();
	    testDelete_more_than_one_rotation();
	}
	
	private static void testDelete_more_than_one_rotation(){
	    Log.en("============testDelete_more_than_one_rotation");
	    
	    AvlNode<Integer> n19=new AvlNode<Integer>(19);
	    n19.updateHeight();
	    
	    AvlNode<Integer> n18=new AvlNode<Integer>(18);
	    n18.setRight(n19);
	    n19.setParent(n18);
	    n18.updateHeight();
	    
	    AvlNode<Integer> n16=new AvlNode<Integer>(16);
	    n16.updateHeight();
	    
	    AvlNode<Integer>n17=new AvlNode<Integer>(17);
	    n17.setRight(n18);
	    n18.setParent(n17);
	    n17.setLeft(n16);
	    n16.setParent(n17);
	    n17.updateHeight();
	    
	    AvlNode<Integer>n11=new AvlNode<Integer>(11);
	    n11.updateHeight();
	    AvlNode<Integer>n13=new AvlNode<Integer>(13);
	    n13.updateHeight();
	    
	    AvlNode<Integer>n12=new AvlNode<Integer>(12);
	    n12.setLeft(n11);
	    n11.setParent(n12);
	    n12.setRight(n13);
	    n13.setParent(n12);
	    n12.updateHeight();
	    
	    AvlNode<Integer>n15=new AvlNode<Integer>(15);
	    n15.setLeft(n12);
	    n12.setParent(n15);
	    n15.setRight(n17);
	    n17.setParent(n15);
	    n15.updateHeight();
	    
	    AvlNode<Integer>n7=new AvlNode<Integer>(7);
	    n7.setHeight(1);
	    
	    AvlNode<Integer>n6=new AvlNode<Integer>(6);
	    n6.setRight(n7);
	    n7.setParent(n6);
	    n6.updateHeight();
	    
	    AvlNode<Integer>n3=new AvlNode<Integer>(3);
	    n3.updateHeight();
	    
	    AvlNode<Integer>n5=new AvlNode<Integer>(5);
	    n5.setLeft(n3);
	    n3.setParent(n5);
	    n5.setRight(n6);
	    n6.setParent(n5);
	    n5.updateHeight();
	    
	    AVLTree<Integer>tree=new AVLTree<Integer>();
        tree.insert(10);
        AvlNode<Integer>root=(AvlNode<Integer>) tree.getRoot();
        root.setLeft(n5);
        n5.setParent(root);
        root.setRight(n15);
        n15.setParent(root);
        root.updateHeight();
        
        Log.en("============Before Delete");
        BinaryTreeTraverse.doit(tree.getRoot());
        
        int target=3;
        Log.en("============After  Delete:"+target);
        tree.remove(target);
        BinaryTreeTraverse.doit(tree.getRoot());
	}
	private static void testDelete(){
	    Log.en("============testDelete");
        AVLTree<Integer>root=new AVLTree<Integer>();
        int max=11;
        for(int i=1;i<=max;i++){
            root.insert(i);
        }
        
        Log.en("============Before Delete");
        BinaryTreeTraverse.doit(root.getRoot());
        
        //remove leaf
        int target=3;
        Log.en("============After  Delete:"+target);
        root.remove(target);
        BinaryTreeTraverse.doit(root.getRoot());
        
        //remove leaf
        target=1;
        Log.en("============After  Delete:"+target);
        root.remove(target);
        BinaryTreeTraverse.doit(root.getRoot());
        
        
        //remove root
        target=6;
        Log.en("============After  Delete:"+target);
        root.remove(target);
        BinaryTreeTraverse.doit(root.getRoot());
	}
	private static void testInsert_Right_Right(){
	    Log.en("============testInsert_Right_Right");
	    AVLTree<Integer>root=new AVLTree<Integer>();
        root.insert(1);
        root.insert(5);
        root.insert(10);
        root.insert(15);
        root.insert(20);
	    BinaryTreeTraverse.doit(root.getRoot());
	}
	private static void testInsert_Right_Left(){
        Log.en("============testInsert_Right_Left");
        AVLTree<Integer>root=new AVLTree<Integer>();
        root.insert(1);
        root.insert(5);
        root.insert(10);
        root.insert(15);
        root.insert(20);
        
        root.insert(11);
        BinaryTreeTraverse.doit(root.getRoot());
    }
	private static void testInsert_Left_Left(){
        Log.en("============testInsert_Left_Left");
        AVLTree<Integer>root=new AVLTree<Integer>();
        root.insert(20);
        root.insert(15);
        root.insert(10);
        root.insert(5);
        root.insert(1);
        BinaryTreeTraverse.doit(root.getRoot());
    }
	private static void testInsert_Left_Right(){
        Log.en("============testInsert_Left_Right");
        AVLTree<Integer>root=new AVLTree<Integer>();
        root.insert(20);
        root.insert(15);
        root.insert(10);
        root.insert(5);
        root.insert(1);
        
        root.insert(11);
        BinaryTreeTraverse.doit(root.getRoot());
    }
}

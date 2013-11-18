package algorithm.tree.binary.avl;

import algorithm.tree.binary.FetchAndRemoveRightMostNode;
import algorithm.tree.binary.FetchAndRemoveRightMostNode.Bean;
import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BSearchTree;

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
					upIsLeft = (parent.getLeft() == child);
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

			child.updateHeight();
			parent.updateHeight();

			replaceForParent = child;
		} else if (!upIsLeft && !downIsLeft) {// right,right
			AvlNode<E> middle = (AvlNode<E>) child.getLeft();

			child.setLeft(parent);
			child.setParent(null);

			parent.setRight(middle);
			parent.setParent(child);

			child.updateHeight();
			parent.updateHeight();

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

			child.updateHeight();
			targetRoot.updateHeight();
			parent.updateHeight();

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

			child.updateHeight();
			targetRoot.updateHeight();
			parent.updateHeight();

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

}

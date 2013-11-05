package algorithm.tree;

import java.util.Stack;

import algorithm.tree.data.BinaryNode;

public class TOperation {
    /**
     * 
     * @param n
     */
    public static void preOrderVisit(BinaryNode n) {
        Stack<BinaryNode> continueT = new Stack<BinaryNode>();
        while (true) {
            if (n != null //&& !n.isMarked()
                            ) {
                System.out.println(n.getName());
                //n.mark();
                if (n.getLeft() == null) {
                    n = n.getRight();
                } else {
                    if (n.getRight() != null) {
                        continueT.push(n.getRight());
                    }
                    n = n.getLeft();
                }
            } else if (continueT.size() > 0) {
                n = continueT.pop();
            } else {
                break;
            }
        }
    }

    /**
     * for a tree which contains a loop, there's no way to determine the
     * left/right order
     * 
     * @param n
     */
    public static void inOrderVisit(BinaryNode n) {
        final class Cont {
            public BinaryNode node;
            public boolean isMiddle;

            public Cont(BinaryNode node, boolean f) {
                this.node = node;
                this.isMiddle = f;
            }
        }
        Stack<Cont> stackT = new Stack<Cont>();
        Cont cur = null;
        boolean useStack = false;
        while (true) {
            if (!useStack) {
                if (n != null && n.isMarked() == false) {
                    if (n.getLeft() == null) {
                        System.out.println(n.getName());
                        n.mark();
                        n = n.getRight();
                    } else {
                        if (n.getRight() != null) {
                            stackT.push(new Cont(n.getRight(), false));
                        }
                        stackT.push(new Cont(n, true));
                        n = n.getLeft();
                    }
                    useStack = false;
                } else {
                    if (stackT.size() <= 0)
                        break;
                    cur = stackT.pop();
                    useStack = true;
                }
            } else {
                if (cur != null && cur.node.isMarked() == false) {
                    if (cur.node == null) {
                        System.out.println("Error. Cont.node can't be null");
                        return;
                    }
                    if (cur.isMiddle) {
                        System.out.println(cur.node.getName());
                        cur.node.mark();
                        cur = stackT.pop();
                        useStack = true;
                    } else {
                        n = cur.node;
                        useStack = false;
                    }
                } else if (stackT.size() > 0) {
                    cur = stackT.pop();
                } else {
                    break;
                }
            }
        }
    }

    public static void visitWithoutExternalMem(BinaryNode root) {
        BinaryNode p = root;
        BinaryNode q = root;
        while (true) {
            if (p == null) {
                break;
            }
            if (p.getVcount() >= 3 && p.isMarked()) {
                BinaryNode tmp = p;
                p = q.getLeft();
                q.setLeft(q.getRight());
                q.setRight(tmp);
            } else {
                p.setVcount(p.getVcount() + 1);
                p.mark();
                System.out.println("visit(" + p.getName() + "," + p.getVcount() + ")");
                if (p.getLeft() == null) {
                    p.setLeft(p.getRight());
                    p.setRight(q);
                    q = null;
                } else {
                    BinaryNode tmp = p.getLeft();
                    p.setLeft(p.getRight());
                    p.setRight(q);
                    q = p;
                    p = tmp;
                }
                if (p == q) {
                    break;
                }
            }
        }
    }
}

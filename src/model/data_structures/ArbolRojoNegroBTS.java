package model.data_structures;

import java.util.NoSuchElementException;

public class ArbolRojoNegroBTS<K extends Comparable<K>, V> {

	private static final boolean RED   = true;
	private static final boolean BLACK = false;

	private NodoHash root;



	public ArbolRojoNegroBTS() 
	{}

	//is node x red; false if x is null ?
	private boolean isRed(NodoHash x) {
		if (x == null) return false;
		return x.isColor() == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int size(NodoHash x) {
		if (x == null) return 0;
		return x.getSize();
	} 


	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Is this symbol table empty?
	 * @return {@code true} if this symbol table is empty and {@code false} otherwise
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	
	/**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private V get(NodoHash x, K key) {
        while (x != null) {
            int cmp = key.compareTo((K)x.darE());
            if      (cmp < 0) x = x.getLeft();
            else if (cmp > 0) x = x.getRight();
            else              return (V) x.darv();
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(K key) {
        return get(key) != null;
    }
    
    /***************************************************************************
     *  Red-black tree insertion.
     ***************************************************************************/

     /**
      * Inserts the specified key-value pair into the symbol table, overwriting the old 
      * value with the new value if the symbol table already contains the specified key.
      * Deletes the specified key (and its associated value) from this symbol table
      * if the specified value is {@code null}.
      *
      * @param key the key
      * @param val the value
      * @throws IllegalArgumentException if {@code key} is {@code null}
      */
     public void put(K key, V val) {
         if (key == null) throw new IllegalArgumentException("first argument to put() is null");
         if (val == null) {
             delete(key);
             return;
         }

         root = put(root, key, val);
         root.setColor(BLACK);
         // assert check();
     }

     // insert the key-value pair in the subtree rooted at h
     private NodoHash put(NodoHash h, K key, V val) { 
         if (h == null) return new NodoHash(key, val, RED, 1);

         int cmp = key.compareTo((K)h.darE());
         if      (cmp < 0) h.setLeft(put(h.getLeft(),  key, val) );
         else if (cmp > 0) h.setRight(put(h.getRight(), key, val) );
         else              h.cambiarV(val);

         // fix-up any right-leaning links
         if (isRed(h.getRight()) && !isRed(h.getLeft()))      h = rotateLeft(h);
         if (isRed(h.getLeft())  &&  isRed(h.getLeft().getLeft())) h = rotateRight(h);
         if (isRed(h.getLeft())  &&  isRed(h.getRight()))     flipColors(h);
         h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);

         return h;
     }
     
     
     /***************************************************************************
      *  Red-black tree deletion.
      ***************************************************************************/

      /**
       * Removes the smallest key and associated value from the symbol table.
       * @throws NoSuchElementException if the symbol table is empty
       */
      public void deleteMin() {
          if (isEmpty()) throw new NoSuchElementException("BST underflow");

          // if both children of root are black, set root to red
          if (!isRed(root.getLeft()) && !isRed(root.getRight()))
              root.setColor(RED);

          root = deleteMin(root);
          if (!isEmpty()) root.setColor(BLACK);
          // assert check();
      }

      // delete the key-value pair with the minimum key rooted at h
      private NodoHash deleteMin(NodoHash h) { 
          if (h.getLeft() == null)
              return null;

          if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
              h = moveRedLeft(h);

          h.getLeft().setLeft(deleteMin(h.getLeft()));
          return balance(h);
      }
      
      /**
       * Removes the largest key and associated value from the symbol table.
       * @throws NoSuchElementException if the symbol table is empty
       */
      public void deleteMax() {
          if (isEmpty()) throw new NoSuchElementException("BST underflow");

          // if both children of root are black, set root to red
          if (!isRed(root.getLeft()) && !isRed(root.getRight()))
              root.setColor(RED);

          root = deleteMax(root);
          if (!isEmpty()) root.setColor(BLACK);
          // assert check();
      }

      // delete the key-value pair with the maximum key rooted at h
      private NodoHash deleteMax(NodoHash h) { 
          if (isRed(h.getLeft()))
              h = rotateRight(h);

          if (h.getRight() == null)
              return null;

          if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft()))
              h = moveRedRight(h);

          h.setRight(deleteMax(h.getRight()));

          return balance(h);
      }
      
      /**
       * Removes the specified key and its associated value from this symbol table     
       * (if the key is in this symbol table).    
       *
       * @param  key the key
       * @throws IllegalArgumentException if {@code key} is {@code null}
       */
      public void delete(K key) { 
          if (key == null) throw new IllegalArgumentException("argument to delete() is null");
          if (!contains(key)) return;

          // if both children of root are black, set root to red
          if (!isRed(root.getLeft()) && !isRed(root.getRight()))
              root.setColor(RED);

          root = delete(root, key);
          if (!isEmpty()) root.setColor(BLACK);
          // assert check();
      }
      

      // delete the key-value pair with the given key rooted at h
      private NodoHash delete(NodoHash h, K key) { 
          // assert get(h, key) != null;

          if (key.compareTo((K)h.darE()) < 0)  {
              if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
                  h = moveRedLeft(h);
              h.setLeft(delete(h.getLeft(), key));
          }
          else {
              if (isRed(h.getLeft()))
                  h = rotateRight(h);
              if (key.compareTo((K)h.darE()) == 0 && (h.getRight() == null))
                  return null;
              if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft()))
                  h = moveRedRight(h);
              if (key.compareTo((K)h.darE()) == 0) {
                  NodoHash x = min(h.getRight());
                  h.cambiarE(x.darE());
                  h.cambiarV(x.darv());
                  // h.val = get(h.right, min(h.right).key);
                  // h.key = min(h.right).key;
                  h.setRight(deleteMin(h.getRight()));
              }
              else h.setRight(delete(h.getRight(), key));
          }
          return balance(h);
      }
      
      /***************************************************************************
       *  Red-black tree helper functions.
       ***************************************************************************/

       // make a left-leaning link lean to the right
       private NodoHash rotateRight(NodoHash h) {
           // assert (h != null) && isRed(h.left);
    	   NodoHash x = h.getLeft();
    	   h.setLeft(x.getRight());
    	   x.setRight(h);
           x.setColor(x.getRight().isColor());
           x.getRight().setColor(RED);
           x.setSize(h.getSize());
           h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
           return x;
       }

       // make a right-leaning link lean to the left
       private NodoHash rotateLeft(NodoHash h) {
           // assert (h != null) && isRed(h.right);
           NodoHash x = h.getRight();
           h.setRight(x.getLeft());
           x.setLeft(h);
           x.setColor(x.getLeft().isColor());
           x.getLeft().setColor(RED);
           x.setSize(h.getSize());
           h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
           return x;
       }

       // flip the colors of a node and its two children
       private void flipColors(NodoHash h) {
           // h must have opposite color of its two children
           // assert (h != null) && (h.left != null) && (h.right != null);
           // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
           //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
    	   h.setColor(!h.isColor());
           h.getLeft().setColor(!h.getLeft().isColor());
           h.getRight().setColor(!h.getRight().isColor());
       }

       // Assuming that h is red and both h.left and h.left.left
       // are black, make h.left or one of its children red.
       private NodoHash moveRedLeft(NodoHash h) {
           // assert (h != null);
           // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

           flipColors(h);
           if (isRed(h.getRight().getLeft())) {
        	   h.setRight(rotateRight(h.getRight()));
               h = rotateLeft(h);
               flipColors(h);
           }
           return h;
       }

       // Assuming that h is red and both h.right and h.right.left
       // are black, make h.right or one of its children red.
       private NodoHash moveRedRight(NodoHash h) {
           // assert (h != null);
           // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
           flipColors(h);
           if (isRed(h.getLeft().getLeft())) { 
               h = rotateRight(h);
               flipColors(h);
           }
           return h;
       }

       // restore red-black tree invariant
       private NodoHash balance(NodoHash h) {
           // assert (h != null);

           if (isRed(h.getRight()))                      h = rotateLeft(h);
           if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft())) h = rotateRight(h);
           if (isRed(h.getLeft()) && isRed(h.getRight()))     flipColors(h);
           
           h.setSize(size(h.getLeft()) + size(h.getRight()) + 1);
           return h;
       }
       
       /**
        * Returns the height of the BST (for debugging).
        * @return the height of the BST (a 1-node tree has height 0)
        */
       public int height() {
           return height(root);
       }
       private int height(NodoHash x) {
           if (x == null) return -1;
           return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
       }
       
       /***************************************************************************
        *  Ordered symbol table methods.
        ***************************************************************************/

        /**
         * Returns the smallest key in the symbol table.
         * @return the smallest key in the symbol table
         * @throws NoSuchElementException if the symbol table is empty
         */
        public K min() {
            if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
            return (K) min(root).darE();
        } 

        // the smallest key in subtree rooted at x; null if no such key
        private NodoHash min(NodoHash x) { 
            // assert x != null;
            if (x.getLeft() == null) return x; 
            else                return min(x.getLeft()); 
        } 

        /**
         * Returns the largest key in the symbol table.
         * @return the largest key in the symbol table
         * @throws NoSuchElementException if the symbol table is empty
         */
        public K max() {
            if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
            return (K)max(root).darE();
        } 

        // the largest key in the subtree rooted at x; null if no such key
       public NodoHash max(NodoHash x) { 
            // assert x != null;
            if (x.getRight() == null) return x; 
            else                 return max(x.getRight()); 
        } 


        /**
         * Returns the largest key in the symbol table less than or equal to {@code key}.
         * @param key the key
         * @return the largest key in the symbol table less than or equal to {@code key}
         * @throws NoSuchElementException if there is no such key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public K floor(K key) {
            if (key == null) throw new IllegalArgumentException("argument to floor() is null");
            if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
            NodoHash x = floor(root, key);
            if (x == null) throw new NoSuchElementException("argument to floor() is too small");
            else           return (K)x.darE();
        }    

        // the largest key in the subtree rooted at x less than or equal to the given key
        private NodoHash floor(NodoHash x, K key) {
            if (x == null) return null;
            int cmp = key.compareTo((K)x.darE());
            if (cmp == 0) return x;
            if (cmp < 0)  return floor(x.getLeft(), key);
            NodoHash t = floor(x.getRight(), key);
            if (t != null) return t; 
            else           return x;
        }

        /**
         * Returns the smallest key in the symbol table greater than or equal to {@code key}.
         * @param key the key
         * @return the smallest key in the symbol table greater than or equal to {@code key}
         * @throws NoSuchElementException if there is no such key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public K ceiling(K key) {
            if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
            if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
            NodoHash x = ceiling(root, key);
            if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
            else           return (K)x.darE();  
        }

        // the smallest key in the subtree rooted at x greater than or equal to the given key
        private NodoHash ceiling(NodoHash x, K key) {  
            if (x == null) return null;
            int cmp = key.compareTo((K)x.darE());
            if (cmp == 0) return x;
            if (cmp > 0)  return ceiling(x.getRight(), key);
            NodoHash t = ceiling(x.getLeft(), key);
            if (t != null) return t; 
            else           return x;
        }

        /**
         * Return the key in the symbol table of a given {@code rank}.
         * This key has the property that there are {@code rank} keys in
         * the symbol table that are smaller. In other words, this key is the
         * ({@code rank}+1)st smallest key in the symbol table.
         *
         * @param  rank the order statistic
         * @return the key in the symbol table of given {@code rank}
         * @throws IllegalArgumentException unless {@code rank} is between 0 and
         *        <em>n</em>�1
         */
        public K select(int rank) {
            if (rank < 0 || rank >= size()) {
                throw new IllegalArgumentException("argument to select() is invalid: " + rank);
            }
            return select(root, rank);
        }

        // Return key in BST rooted at x of given rank.
        // Precondition: rank is in legal range.
        private K select(NodoHash x, int rank) {
            if (x == null) return null;
            int leftSize = size(x.getLeft());
            if      (leftSize > rank) return select(x.getLeft(),  rank);
            else if (leftSize < rank) return select(x.getRight(), rank - leftSize - 1); 
            else                      return (K)x.darE();
        }

        /**
         * Return the number of keys in the symbol table strictly less than {@code key}.
         * @param key the key
         * @return the number of keys in the symbol table strictly less than {@code key}
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public int rank(K key) {
            if (key == null) throw new IllegalArgumentException("argument to rank() is null");
            return rank(key, root);
        } 

        // number of keys less than key in the subtree rooted at x
        private int rank(K key, NodoHash x) {
            if (x == null) return 0; 
            int cmp = key.compareTo((K)x.darE()); 
            if      (cmp < 0) return rank(key, x.getLeft()); 
            else if (cmp > 0) return 1 + size(x.getLeft()) + rank(key, x.getRight()); 
            else              return size(x.getLeft()); 
        } 

        /**
         * Returns the number of keys in the symbol table in the given range.
         *
         * @param  lo minimum endpoint
         * @param  hi maximum endpoint
         * @return the number of keys in the symbol table between {@code lo} 
         *    (inclusive) and {@code hi} (inclusive)
         * @throws IllegalArgumentException if either {@code lo} or {@code hi}
         *    is {@code null}
         */
        public int size(K lo, K hi) {
            if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
            if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

            if (lo.compareTo(hi) > 0) return 0;
            if (contains(hi)) return rank(hi) - rank(lo) + 1;
            else              return rank(hi) - rank(lo);
        }

		public NodoHash getRoot() {
			return root;
		}
		public Iterable<K> keys(K lo, K hi) {
	        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
	        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

	        ListaDoblementeEncadenada<K> queue = new ListaDoblementeEncadenada<K>();
	        if (isEmpty() || lo.compareTo(hi) > 0) return queue;
	        keys(root, queue, lo, hi);
	        return queue;
	    } 

	    // add the keys between lo and hi in the subtree rooted at x
	    // to the queue
	    private void keys(NodoHash<K, V> x, ListaDoblementeEncadenada<K> queue, K lo, K hi) { 
	        if (x == null) return; 
	        int cmplo = lo.compareTo(x.darE()); 
	        int cmphi = hi.compareTo(x.darE()); 
	        if (cmplo < 0) keys(x.getLeft(), queue, lo, hi); 
	        if (cmplo <= 0 && cmphi >= 0) queue.insertarComienzo(x.darE()); 
	        if (cmphi > 0) keys(x.getRight(), queue, lo, hi); 
	    } 

       

    
    

}
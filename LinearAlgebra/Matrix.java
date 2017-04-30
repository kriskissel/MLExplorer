package LinearAlgebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.IntegerTuple;

public class Matrix {
    
    /**
     * Stores a matrix as an array of arrays of doubles.
     * Immutable data type.
     * 
     */
    private double[] array; // stores entries of matrix in a flattened array
    private int m, n; // rows, columns
    private int rank; // computed lazily
    private Double det; // determinant (computed lazily) - only meaningful if matrix is square
    private Matrix R; // row echelon form computed via Gauss Elimination
    private Matrix L; // for LU decomposition
    private List<IntegerTuple> permutations; // record of permutations performed during elimination
    private Matrix P; // for PA=LU decomposition
    private Matrix tpose; // for caching the transpose of this matrix
    

    
    /**
     * 
     * @param array must have length equal to rows*columns
     * @param rows
     * @param columns
     */
    public Matrix(double[] array, int rows, int columns) {
        this.m = rows;
        this.n = columns;
        this.array = new double[rows * columns];
        for (int k = 0; k < rows * columns; k++){
            this.array[k] = array[k];
        }
    }
    
    /**
     * 
     * @param array must have length equal to rows*columns
     * @param rows
     * @param columns
     */
    public Matrix(Double[] array, int rows, int columns) {
        this.m = rows;
        this.n = columns;
        this.array = new double[rows * columns];
        for (int k = 0; k < rows * columns; k++){
            this.array[k] = array[k];
        }
    }
    
    /**
     * 
     * @param m number of rows
     * @param n number of columns
     * @return an m x n matrix of all zeros
     */
    public static Matrix zeros(int m, int n){
        return new Matrix(new double[m*n], m, n);
    }
    
    /**
     * 
     * @return the rank of this matrix
     */
    public int rank() {
        if (this.R == null) {
            rowEchelonForm();
        }
        return rank;
    }
    
    
    
    /*
     * Can make this print prettier if we align rows and columns better.
     * Can also improve display for large matrices.
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(array[n * i + j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * 
     * @return an array representing a flattened version of this matrix
     */
    public double[] toDoubleArray() {
        
        // avoid rep exposure by returning a copy of this.array
        double[] array = Arrays.copyOf(this.array, this.array.length);
        return array;
    }
    
    /**
     * 
     * @param row row number, 0-indexed
     * @param col column number, 0-indexed
     * @return entry of matrix in given row, column
     */
    public double get(int row, int col) { return array[row * n + col];}
    
    /**
     * 
     * @return number of rows in this matrix
     */
    public int rows() {return m;}
    
    /**
     * 
     * @return number of columns in thi matrix
     */
    public int columns() {return n;}
    
    /**
     * 
     * @return a new Matrix instance with the same entries as this instance
     */
    public Matrix copy(){
        return new Matrix(this.array, this.m, this.n);
    }
    
    /**
     * 
     * @return the determinant 0f this matrix
     * @throws RunTimeException if this matrix is not square
     */
    public double determinant() {
        if (this.m != this.n) {throw new RuntimeException("Cannot take determinant " +
                    "of non-square matrix.");
        }
        if (this.det != null) {return this.det;}
        
        // if det is still null, the row echelon form R hasn't been computed, either
        rowEchelonForm();
        return this.det;
    }
    

    /**
     * 
     * @return row echelon form of this matrix obtained by partial pivoting
     * pivots are chosen left to right, and are selected using the largest
     * available value in the column by performing any necessary row
     * exchanges.  This is also the upper triangular matrix U in a PA=LU
     * decomposition of this matrix.
     */
    public Matrix rowEchelonForm() {
        
        // we've cached the row echelon form if we've computed it before
        if (this.R != null) {return this.R;}
        
        // initialize record of permutations
        this.permutations = new ArrayList<IntegerTuple>(); 
        double d = 1; // used to track the determinant
        
        // initilize this.rank to 0 and increase by 1 each time we find a pivot
        this.rank = 0;
        
        // start with a copy of this matrix and initiliaze L with 1s on diagonal
        this.R = this.copy();
        this.L = identity(this.m);
        
        
        // perform Gauss Elimination
        // our first run through will determine necessary row exchanges
        // if there are any such exchanges, we will need to run through the elimination
        // a second time after having performed all the row exchanges in advance.
        
        boolean exchanges = false;
        int thisRow = 0; // keep track of current row, will advance inside loop
        int col = 0;
        
        // beginning of elimination phase
        while (thisRow < this.m && col < this.n){
            
            // in each column, find the row below thisRow with the largest absolute value
            // swap rows to make this the pivot
            // find the largest value in this column at or below the current 
            // row to use as a pivot
            int rowWithLargestValue = thisRow;
            double largestValue = Math.abs(this.R.get(thisRow, col));
            for (int row = thisRow+1; row < m; row++){
                if (Math.abs(this.R.get(row, col)) > largestValue) {
                    largestValue = Math.abs(this.R.get(row, col));
                    rowWithLargestValue = row;
                }
            }
            
            // if largestValueFound == 0, we need to advance to the next column but not the
            // next row, and the determinant (if it is meaningful) is 0
            if (largestValue == 0) {
                d = 0;
                col++;
                continue;
            }
            
            // otherwise, largestValueFound != 0, so we have a pivot so we add 1 to the rank
            
            // we swap rows to get the pivot
            // we also record the row swap in our list of permutations
            if (rowWithLargestValue != thisRow) {
                exchanges = true;
                R.swapRows(thisRow, rowWithLargestValue);
                permutations.add(new IntegerTuple(thisRow, rowWithLargestValue));
                d *= -1; // row exchange changes the sign of the determinant
            }
            
            // zero out the rows below the current row
            // if the pivot is zero, all the other values below it are also zero, so we
            // only need to do this if the pivot is not zero (in which case it's not really a pivot
            // if (largestValue != 0){
            for (int row = thisRow + 1; row < m; row++) {
                double l = R.get(row,col) / R.get(thisRow, col);
                R.eliminateRowInArray(thisRow, row, l);
                L.array[this.n * row + thisRow] = l;
            }
            
            d *= R.array[n * thisRow + col]; // determinant equals the product of the diagonal entries
            thisRow++;
            col++;
            this.rank++;
        } // end of elimination phase while loop
        
        
        // if we did any row exchanges, we now redo the whole process after performing
        // all necessary exchanges in advance on a copy of the matrix
        
        if (exchanges) {
            d = 1.0;
            Matrix temp = this.copy();
            this.L = Matrix.identity(this.m);
            for (IntegerTuple p : permutations){
                temp.swapRows(p.getX(), p.getY());
                d *= -1.0;
            }
            thisRow = 0; 
            col = 0;
            while (thisRow < this.m && col < this.n) {
                // zero out the rows below the current row
                // if the pivot is zero, all the other values below it are also zero, so we
                // only need to do this if the pivot is not zero (in which case it's not really a pivot
                if (temp.get(thisRow, col) == 0) {
                    col++;
                    continue;
                }
                
                for (int row = thisRow + 1; row < m; row++) {
                    double l = temp.get(row,col) / temp.get(thisRow, col);
                    temp.eliminateRowInArray(thisRow, row, l);
                    this.L.array[this.n * row + thisRow] = l;
                }
                    
                d *= temp.array[n * thisRow + col];
                thisRow++;
                col++;
            }
        this.R = temp;
        }
        
        
        // record the determinant in both R and the current matrix.
        this.det = d;
        
        // some extra caching of results to reduce computation time later
        this.R.det = d;
        this.R.R = R; // R is its own row echelon form
        this.R.L = Matrix.identity(R.m);
        
        this.L.det = 1.0;
        this.L.L = this.L;
        this.L.R = Matrix.identity(L.n);
        
        // finally, return the row echelon form
        return this.R;
    }
    
    /**
     * 
     * @return lower triangular matrix U in PA=LU decomposition of this matrix
     */
    public Matrix getL(){
        if (this.L == null) { rowEchelonForm();}
        return this.L;
    }
    
    /**
     * 
     * @return row echelon form of this matrix (see rowEchelonForm() method 
     * description)
     */
    public Matrix getR(){
        if (this.R != null) { return R;}
        return rowEchelonForm();
    }
    
    
    /**
     * 
     * @return upper triangular matrix U in PA=LU decomposition of this matrix
     */
    public Matrix getU(){
        if (this.R != null) { return R;}
        return rowEchelonForm();
    }
    
    
    
    /**
     * 
     * @return permutation matrix P in PA=U decompoition of this matrix
     */
    public Matrix getP(){
        if (this.R == null) { rowEchelonForm(); }
        if (this.P == null) {
            
            // construct an m x m identity matrix
            this.P = Matrix.identity(this.m);
            
            // then swap the rows appropriately
            for (IntegerTuple p : this.permutations){
                this.P.swapRows(p.getX(), p.getY());
             }
        }
        return this.P;
    }
    
    
    /**
     * This method should be used instead of equals to avoid comparing
     * floating point values for equality.
     * 
     * @param o other matrix, must have same dimensions as this matrix
     * @param tolerance a positive number
     * @return true iff every entry of o is within tolerance of its
     * corresponding entry in this matrix
     */
    public boolean closeTo(Matrix o, double tolerance){
        if (o.m != this.m || o.n != this.n) {return false;}
        for (int row = 0; row < this.m; row++){
            for (int col = 0; col < this.n; col++) {
                if (Math.abs(o.get(row, col) - this.get(row, col)) >= tolerance) { 
                    return false; }
            }
        }
        return true;
    }
    
    /*
     * exchanges rows r1 and r2 in this matrix
     * since this mutates the state data of this matrix, it should never
     * be run after other state data, such as thr row echelon form, have
     * been cached.
     */
    private void swapRows(int r1, int r2){
        double tmp;
        for (int j = 0; j < this.n; j++){
            tmp = array[this.n * r1 + j];
            array[this.n * r1 + j] = array[this.n * r2 + j];
            array[this.n * r2 + j] = tmp;
        }
    }
    
    /*
     * subtract l times row r1 from row r2
     */
    private void eliminateRowInArray(int r1, int r2, double l){
        for (int col = 0; col < this.n; col++){
            array[n * r2 + col] -= l * array[n * r1 + col];
        }
    }
    
    /**
     * Performs one step of elimination on a matrix
     * @param r1 row to use
     * @param r2 row to change
     * @param l mulitple of row to use
     * @return matrix with row r2 replaced by row r2 - l times row r1
     */
    public Matrix eliminateRow(int r1, int r2, double l) {
        Matrix newMatrix = this.copy();
        newMatrix.eliminateRowInArray(r1, r2, l);
        return newMatrix;
    }
    
    /**
     * 
     * @return the transpose of this matrix
     */
    public Matrix transpose() {
        if (this.tpose != null) { return this.tpose;}
        double[] atarray = new double[n * m];
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j++) {
                atarray[m * j + i] = this.array[n * i + j];
            }
        }
        this.tpose = new Matrix(atarray, n, m);
        this.tpose.tpose = this;
        return this.tpose;
    }
    
    /**
     * 
     * @param c a scaler
     * @return c times this matrix
     */
    public Matrix times(Double c){
        double[] newArray = new double[m * n];
        for (int k = 0; k < m * n; k++) {
            newArray[k] = c * this.array[k];
        }
        Matrix productWithScalar = new Matrix(newArray, m, n);

        return productWithScalar;
    }
    
    /**
     * 
     * @param matrix must be compatible size for matrix multiplication
     * @return matrix product this*matrix
     */
    public Matrix times(Matrix matrix){
        double[] newArray = new double[this.m * matrix.n];
        for (int i = 0; i < m; i++) {
            for (int j=0; j < matrix.n; j++) {
                double partialSum = 0.0;
                for (int k = 0; k < this.n; k++) {
                    partialSum += this.get(i, k) * matrix.get(k, j);
                }
                newArray[matrix.n * i + j] = partialSum;
            }
        }
        return new Matrix(newArray, this.m, matrix.n);
    }
    

    /**
     * Requires that Ax=b has a solution, where A is this matrix
     * @param b a vector or matrix with the same number of rows as this matrix
     * @return x, a solution of Ax=b, where A is this matrix
     * 
     */
    public Matrix solve(Matrix b){
        if (this.m == this.n) {return this.solveForSquareSystems(b);}
        
        // if there is no solution, then I'd like to return the least-squares approximate
        // solution, so i'll need to wrap the above in a try-catch
        // can also add a separate method for solveTrueOnly
        
        // also still need to implement approach for non-square systems.
        // can probably use least-squares if more rows than columns
        // but will need to find something else (least-norm solution?) if more
        // columns than rows.
        
        return null;
    }
    

    /*
     * Uses PA=LU decomposition to solve Ax=b
     */
    private Matrix solveForSquareSystems(Matrix b){
        
        // start by applying any necessary permutations to b
        Matrix rightSide = b.copy();
        if (R == null) {rowEchelonForm();}
        for (IntegerTuple p : permutations) {
            //System.out.println(p.getX() + " <--> " + p.getY());
            rightSide.swapRows(p.getX(), p.getY());
        }

        // next we use the LU decomposition
        // first we solve Ly = b using forward substitution;
        double[] y = forwardSubstituion(rightSide.array);

        // then use backsubstitution to solve Ux = y;
        double[] x = backSubstitution(y);
        
        return new Matrix(x, this.n, 1);
    }
    
    private double[] forwardSubstituion(double[] b){
        // performs the forward substitution step in solving Ly=b
        // L is m-by-m, so y will be an m-dimensional vector
        // recall that L has ones on the diagonal and is lower triangular
        // hence the kth row corresponds to an equation of the form
        // c_0*y_0+c_1*y_1+...+c_(k-1)*y_(k-1) + y_k = b_k
        // express this as partial_sum + y_k = b_k
        
        double[] y = new double[this.m];
        // k is the row number indexed from 0
        for (int k = 0; k < this.m; k++){
            
            // accumulator for partial sum
            double partial_sum = 0; 
            
            // j is the column number
            for (int j = 0; j < k; j++) { partial_sum += L.get(k, j) * y[j]; }
            y[k] = b[k]- partial_sum;
        }
        return y;
    }
    
    private double[] backSubstitution(double[] y){
        // performs the backsubstitution step in solving Ux=y
        // U is n-by-n, so y will be an n-dimensional vector
        // recall that U is uppertriangular.
        // we start at the bottom row of U and work our way up
        // each row represents an equatyion of the form
        // c_k*x_k + c_(k+1)*c_(k+1) + ... + c_n * x_n = y_n
        // each row allows us to solve for one value x_k
        // if there are any rows of the matrix where the coefficients are all 0 but
        // the right side vector y is not zero in the same row this system has no solution
        // in that case, we throw a RuntimeException
        // If a system has multiple solution, we return the one the particular solution
        // that corresponds to all free variables being zero
        
        double[] x = new double[this.n];
        for (int i = this.n-1; i >= 0; i--) {
            int k = 0;
            while (k < this.n && this.R.get(i, k) == 0) {k++;} // advance to the first non-zero entry
            if (k == this.n) { 
                if (y[i] != 0) { throw new RuntimeException("System has" +
                        "no solution"); // this row represents the equation 0 = non-zero
                }
            else {continue;} // this row represents the equation 0 = 0
            }
            
            // accumulator
            double partial_sum = 0; 
            for (int j = k+1; j < this.n; j++) { partial_sum += this.R.get(i, j) * x[j];}
            x[k] = (y[i] - partial_sum) / this.R.get(i, k);
        }
        return x;
    }
    
    
    public static Matrix identity(int n){
        double[] identityArray = new double[n * n];
        for (int i = 0; i < n; i++){
            identityArray[n * i+i] = 1.0;
        }
        return new Matrix(identityArray, n, n);
    }

}

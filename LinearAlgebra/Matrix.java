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
    
    // constructor for an m x n matrix of all zeros
    public Matrix zeros(int m, int n){
        return new Matrix(new double[m*n], m, n);
    }
    
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
    
    public double get(int row, int col) { return array[row * n + col];}
    
    public int rows() {return m;}
    
    public int columns() {return n;}
    
    // public int rank()
    
    // public double determinant()
    
    public Matrix copy(){
        return new Matrix(this.array, this.m, this.n);
    }
    
    public double determinant() {
        if (this.m != this.n) {throw new RuntimeException("Cannot take determinant " +
                    "of non-square matrix.");
        }
        if (this.det != null) {return this.det;}
        // if det is still null, the row echelon form R hasn't been computed, either
        rowEchelonForm();
        return this.det;
    }
    

    public Matrix rowEchelonForm() {
        // we've cached the row echelon form if we've computed it before
        if (this.R != null) {return this.R;}
        this.permutations = new ArrayList<IntegerTuple>(); // initialize record of permutations
        double d = 1; // used to track the determinant
        this.rank = 0;
        // start with a copy of this matrix
        this.R = this.copy();
        this.L = identity(this.m);
        //System.out.println("Starting to reduce:");
        //System.out.println(this.R);
        //double[] Larray = new double[this.m * this.m]; // used to form LU decomposition
        // now perform Gauss Elimination
        int thisRow = 0; // keep track of current row, will advance inside loop
        int col = 0;
        while (thisRow < this.m && col < this.n){
            //System.out.println("looking at entry in row "+thisRow+"and column "+col);
            // in each column, find the row below thisRow with the largest absolute value
            // swap rows to make this the pivot
            // find the largest value in this column at or below the current 
            // row to use as a pivot
            int rowWithLargestValue = thisRow;
            double largestValue = Math.abs(this.R.array[n * thisRow + col]);
            for (int row = thisRow+1; row < m; row++){
                if (Math.abs(R.array[n * row + col]) > largestValue) {
                    largestValue = Math.abs(R.array[n * row + col]);
                    rowWithLargestValue = row;
                }
            }
            // if largestValueFound != 0, we have a pivot so we add 1 to the rank
            // if largestValueFound == 0, we need to advance to the next column but not the
            // next row, and the determinant (if it is meaningful) is 0
            if (largestValue == 0) {
                d = 0;
                col++;
                continue;
            }
            
            // we swap rows to get the pivot
            if (rowWithLargestValue != thisRow) {
                R.swapRows(thisRow, rowWithLargestValue);
                permutations.add(new IntegerTuple(thisRow, rowWithLargestValue));
                d *= -1; // row exchange changes the sign of the determinant
            }
            // now zero out the rows below the current row
            // if the pivot is zero, all the other values below it are also zero, so we
            // only need to do this if the pivot is not zero (in which case it's not really a pivot
            if (largestValue != 0){
                for (int row = thisRow + 1; row < m; row++) {
                    double l = R.get(row,col) / R.get(thisRow, col);
                    R.eliminateRow(thisRow, row, l);
                    L.array[this.n * row + thisRow] = l;
                }
            }
            d *= R.array[n * thisRow + col]; // determinant equals the product of the diagonal entries
            thisRow++;
            col++;
            this.rank++;
        }
        // record the determinant in both R and the current matrix.
        R.det = d;
        this.det = d;
        R.R = R; // R is its own row echelon form
        // finish L with 1s on diagonal
        //for (int i = 0; i < this.m; i++) {Larray[this.n * i + i] = 1.0;}
        // cache it
        //this.L = new Matrix(Larray, this.m, this.n);
        this.L.L = this.L;
        return this.R;
    }
    
    public Matrix getL(){
        if (this.L == null) { rowEchelonForm();}
        return this.L;
    }
    
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
    
    private void swapRows(int r1, int r2){
        double tmp;
        for (int j = 0; j < this.n; j++){
            tmp = array[m * r1 + j];
            array[n * r1 + j] = array[n * r2 + j];
            array[n * r2 + j] = tmp;
        }
    }
    
    // subtract l times r1 from r2
    private void eliminateRow(int r1, int r2, double l){
        for (int col = 0; col < this.n; col++){
            array[n * r2 + col] -= l * array[n * r1 + col];
        }
    }
    
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
    
    public Matrix times(Double c){
        double[] newArray = new double[m * n];
        for (int k = 0; k < m * n; k++) {
            newArray[k] = c * this.array[k];
        }
        Matrix productWithScalar = new Matrix(newArray, m, n);
        //
        // consider also updating the L, R and det values of the product matrix
        //
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
                double sum = 0.0;
                for (int k = 0; k < this.n; k++) {
                    sum += this.array[this.n * i + k] * matrix.array[matrix.n * k + j];
                }
                newArray[m * i+j] = sum;
            }
        }
        return new Matrix(newArray, this.m, matrix.n);
    }
    
    // for solving Ax=b, finds a particular solution when a solution exists
    // will use PA=LU decomposition
    // that means we'll also need to keep track of the permutations found
    // in that factorization
    public Matrix solve(Matrix b){
        // start by applying any necessary permutations to b
        Matrix rightSide = b.copy();
        for (IntegerTuple p : permutations) {
            rightSide.swapRows(p.getX(), p.getY());
        }
        // next we use the LU decomposition
        // first we solve Ux = b using forward substitution;
        
        // need to keep track of pivot columns during lu factorization
        // so that I can skip over them during the forward propagation process
        
        return null;
    }
    
    
    
    public static Matrix identity(int n){
        double[] identityArray = new double[n * n];
        for (int i = 0; i < n; i++){
            identityArray[n * i+i] = 1.0;
        }
        return new Matrix(identityArray, n, n);
    }

}

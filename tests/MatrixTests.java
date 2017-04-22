package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import LinearAlgebra.Matrix;

public class MatrixTests {
    
    
    // need to write tests for Lu decomposition when matrix us not square


    @Test
    public void testToString() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        assertEquals(matrix1.toString(), "2.0 3.0 \n1.0 2.0 \n");
    }
    
    
    @Test
    public void testGet() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        assertEquals(matrix1.get(0, 1), 3.0, 0.001);
    }
    
    
    @Test
    public void testCopy() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        Matrix c = matrix1.copy();
        for (int row = 0; row < 2; row++){
            for (int col = 0; col < 2; col++) {
                assertEquals(matrix1.get(row, col), c.get(row, col), 0.001);
            }
        }
    }
    
    
    @Test
    public void immutability1(){
        double[] array = new double[] {2.0, 3.0, 1.0, 2.0};
        Matrix matrix1 = new Matrix(array, 2, 2);
        assertEquals(matrix1.get(0, 0),2.0,0.0001);
        array[0] = 5.0;
        assertEquals(matrix1.get(0, 0), 2.0, 0.0001);
    }
    
    
    @Test
    public void testDeterminant1() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        assertEquals(matrix1.determinant(), 1.0, 0.001);
    }
    
    
    @Test
    public void testDeterminant2() {
        Matrix matrix2 = new Matrix(new double[] {1.0, 2.0, 2.0, 3.0}, 2, 2);
        assertEquals(matrix2.determinant(), -1.0, 0.001);
    }
    
    
    @Test
    public void testCloseTo1() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        Matrix matrix3 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        assertTrue(matrix1.closeTo(matrix3, 0.00001));
    }
    
    
    @Test
    public void testCloseTo2() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        Matrix matrix2 = new Matrix(new double[] {1.0, 2.0, 2.0, 3.0}, 2, 2);
        assertFalse(matrix1.closeTo(matrix2, 0.1));
    }
    
    
    @Test 
    public void testGetL1() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        Matrix L1 = new Matrix(new double[] {1.0, 0.0, 0.5, 1.0}, 2, 2);
        assertTrue(matrix1.getL().closeTo(L1, 0.00001));
    }
    
    @Test
    public void testRank1() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        assertEquals(matrix1.rank(), 2);
    }
    
    
    @Test
    public void testRank2() {
        Matrix matrix4 = new Matrix(new double[] {2.0, 3.0, 2.0, 3.0}, 2, 2);
        assertEquals(matrix4.rank(), 1);
    }
    
    
    @Test
    public void testRank3() {
        Matrix matrix5 = new Matrix(new double[] {0.0, 0.0, 0.0, 0.0}, 2, 2);
        assertEquals(matrix5.rank(), 0);
    }
    
    @Test
    public void testRank4() {
        Matrix matrix6 = new Matrix(new double[] {1.0, 2.0, 3.0, 0.0, 2.0, 0.0}, 2, 3);
        assertEquals(matrix6.rank(), 2);
    }
    
    
    
    @Test
    public void testRowEchelonForm1() {
        Matrix matrix8 = new Matrix(new double[] {2.0, 2.0, 3.0, 2.0, 3.0, 0.0}, 2, 3);
        Matrix matrix9 = new Matrix(new double[] {2.0, 2.0, 3.0, 0.0, 1.0, -3.0}, 2, 3);
        assertTrue(matrix9.closeTo(matrix8.rowEchelonForm(), 0.0001));
    }
    
    @Test
    public void testRowEchelonForm2() {
        Matrix matrix10 = new Matrix(new double[] {1.0, 2.0, 3.0, 2.0, 3.0, 0.0}, 2, 3);
        Matrix matrix11 = new Matrix(new double[] {2.0, 3.0, 0.0, 0.0, 0.5, 3.0}, 2, 3);
        assertTrue(matrix11.closeTo(matrix10.rowEchelonForm(), 0.0001));
    }
    
    @Test
    public void testRowEchelonForm3() {
        Matrix matrix12 = new Matrix(new double[] {0.0, 2.0, 2.0, 0.0, 1.0, 3.0}, 2, 3);
        Matrix matrix13 = new Matrix(new double[] {0.0, 2.0, 2.0, 0.0, 0.0, 2.0}, 2, 3);
        assertTrue(matrix13.closeTo(matrix12.rowEchelonForm(), 0.0001));
    }
    
    @Test
    public void testRowEchelonForm4() {
        Matrix matrix10 = new Matrix(new double[] {1.0, 2.0, 3.0, 2.0, 3.0, 0.0}, 2, 3);
        Matrix matrix12 = new Matrix(new double[] {0.0, 2.0, 2.0, 0.0, 1.0, 3.0}, 2, 3);
        assertFalse(matrix12.closeTo(matrix10.rowEchelonForm(), 0.01));
    }
    
    
    @Test
    public void testTranspose() {
        Matrix matrix14 = new Matrix(new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0}, 2, 3);
        Matrix matrix15 = new Matrix(new double[] {1.0, 4.0, 2.0, 5.0, 3.0, 6.0}, 3, 2);
        assertTrue(matrix14.transpose().closeTo(matrix15, 0.00001));
        assertTrue(matrix15.transpose().closeTo(matrix14, 0.00001));
    }
    
    
    @Test
    public void testTimesWithScalar1() {
        Matrix matrix14 = new Matrix(new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0}, 2, 3);
        Matrix matrix16 = new Matrix(new double[] {3.0, 6.0, 9.0, 12.0, 15.0, 18.0}, 2, 3);
        assertTrue(matrix14.times(3.0).closeTo(matrix16, 0.00001));
    }
    
    @Test
    public void testTimesWithScalar2() {
        Matrix matrix14 = new Matrix(new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0}, 2, 3);
        Matrix matrix17 = new Matrix(new double[] {-3.0, -6.0, -9.0, -12.0, -15.0, -18.0}, 2, 3);
        assertTrue(matrix14.times(-3.0).closeTo(matrix17, 0.00001));
    }
    
    
    @Test
    public void testTimesWithMatrix1() {
        Matrix matrix1 = new Matrix(new double[] {2.0, 3.0, 1.0, 2.0}, 2, 2);
        Matrix matrix18 = new Matrix(new double[] {7.0, 12.0, 4.0, 7.0}, 2, 2);
        assertTrue(matrix1.times(matrix1).closeTo(matrix18, 0.00001));
    }
    
    @Test
    public void testTimesWithMatrix2() {
        Matrix matrix19 = new Matrix(new double[] {1.0, 1.0, 1.0, 2.0, 2.0, 2.0}, 2, 3);
        Matrix matrix20 = new Matrix(new double[] {0.0, 1.0, 2.0, 3.0, 1.0, 0.0}, 3, 2);
        Matrix matrix21 = new Matrix(new double[] {3.0, 4.0, 6.0, 8.0}, 2, 2);
        Matrix matrix22 = new Matrix(new double[] 
                {2.0, 2.0, 2.0, 8.0, 8.0, 8.0, 1.0, 1.0, 1.0}, 3, 3);
        assertTrue(matrix19.times(matrix20).closeTo(matrix21, 0.00001));
        assertTrue(matrix20.times(matrix19).closeTo(matrix22, 0.00001));
    }
    
    @Test
    public void testSolve1() {
        Matrix A = new Matrix(new double[] {2.0, 3.0, 3.0, 2.0}, 2, 2);
        Matrix b = new Matrix(new double[] {8.0, 7.0}, 2, 1);
        Matrix x = new Matrix(new double[] {1.0, 2.0}, 2, 1);
        assertTrue(A.solve(b).closeTo(x, 0.00001));
    }
    
    @Test
    public void testSolve2() {
        Matrix A = new Matrix(new double[] {1.0, 1.0, 1.0, 2.0, 2.0, 3.0, 1.0, 1.0, 1.0},
                3, 3);
        Matrix b = new Matrix(new double[] {2.0, 5.0, 2.0}, 3, 1);
        Matrix x = new Matrix(new double[] {1.0, 0.0, 1.0}, 3, 1);
        System.out.println(A);
        System.out.println(b);
        System.out.println(x);
        System.out.println(A.solve(b));
        assertTrue(A.solve(b).closeTo(x, 0.00001));
    }
    
    

}

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
    public void testTimesWithMatrix3() {
        Matrix m1 = new Matrix(new double[] {1.0, 2.0, 2.0, 3.0}, 2, 2);
        //System.out.println(m1);
        Matrix m2 = new Matrix(new double[] {3.0, 1.0}, 2, 1);
        //System.out.println(m2);
        Matrix p = new Matrix(new double[] {5.0, 9.0}, 2, 1);
        //System.out.println(p);
        //System.out.println(m1.times(m2));
        assertTrue(m1.times(m2).closeTo(p, 0.001));
    }
    
    
    @Test
    public void testTimesWithMatrix4() {
        Matrix L = new Matrix(new double[] {
                1.0000, 0.0000, 0.0000, 0.0, 
                0.1667, 1.0000, 0.0000, 0.0,
                0.3889, 0.4318, 1.0000, 0.0,
                0.1111, 0.4773, 0.9575, 1.0},
                4, 4);
        Matrix U = new Matrix(new double[] {
                36.000, 98.000, 276.00, 794.00, 
                0.0000, -4.889, -16.67, -52.22,
                0.0000, 0.0000, -2.136, -10.23,
                0.0000, 0.0000, 0.0000, 0.3829},
                4, 4);
        Matrix correctLU = new Matrix(new double[] {
                36.000, 98.000, 276.00, 794.00, 
                6.0012, 11.448, 29.229, 80.139,
                14.000, 36.001, 98.002, 276.01,
                3.9996, 8.5543, 20.662, 53.877},
                4, 4);
        
        Matrix LU = L.times(U);
        //System.out.println("******LU*******");
        //System.out.println(LU);
        //System.out.println("**correctLU****");
        //System.out.println(correctLU);
        assert(LU.closeTo(correctLU, 0.2));
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
        //System.out.println(A);
        //System.out.println(b);
        //System.out.println(x);
        //System.out.println(A.solve(b));
        assertTrue(A.solve(b).closeTo(x, 0.00001));
    }
    
    @Test 
    public void testSolve3() {
        Matrix A = new Matrix(new double[] {3.0, 3.0, 5.0, 3.0, 5.0, 9.0, 5.0, 9.0,
                17.0},
                3, 3);
        Matrix b = new Matrix(new double[] {8.5, 14.5, 27.5}, 3, 1);
        Matrix x = new Matrix(new double[] {0.5, -1.0, 2.0}, 3, 1);
        //System.out.println("A=");
        //System.out.println(A);
        //System.out.println("b=");
        //System.out.println(b);
        //System.out.println("Correct solution of Ax=b is:");
        //System.out.println(x);
        //System.out.println(A.solve(b));
        assertTrue(A.solve(b).closeTo(x, 0.00001));
    }
    
    @Test 
    public void testSolve4() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix b = new Matrix(new double[] {-10.0, -34.0, -102.0, -304.0}, 4, 1);
        Matrix x = new Matrix(new double[] {1.0, -1.0, 2.0, -1.0}, 4, 1);
        //System.out.println("A=");
        //System.out.println(A);
        //System.out.println("b=");
        //System.out.println(b);
        //System.out.println("Correct solution of Ax=b is:");
        //System.out.println(x);
        //System.out.println(A.solve(b));
        assertTrue(A.solve(b).closeTo(x, 0.00001));
    }
    
    @Test
    public void testLUDecomposition1() {
        Matrix A = new Matrix(new double[] {
                1.0, 2.0,
                3.0, 4.0},
                2, 2);
        Matrix L = A.getL();
        Matrix U = A.getR();
        Matrix P = A.getP();
        assertTrue(P.times(A).closeTo(L.times(U), 0.01));
    }
    
    @Test
    public void testLUDecomposition2() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix L = A.getL();
        
        Matrix correctL = new Matrix(new double[] {
                1.0000, 0.0000, 0.0000, 0.0, 
                0.1111, 1.0000, 0.0000, 0.0,
                0.3889, 0.4318, 1.0000, 0.0,
                0.1667, 0.4773, 0.9575, 1.0},
                4, 4);

        //System.out.println("A=");
        //System.out.println(A);

        //System.out.println("L=");
        //System.out.println(L);
        
        //System.out.println("correctL=");
        //System.out.println(correctL);
        
        assertTrue(L.closeTo(correctL, 0.01));
    }
    
    @Test
    public void testLUDecomposition3() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        
        Matrix U = A.getR();
        
        Matrix correctU = new Matrix(new double[] {
                36.000, 98.000, 276.00, 794.00, 
                0.0000, -4.889, -16.67, -52.22,
                0.0000, 0.0000, -2.136, -10.23,
                0.0000, 0.0000, 0.0000, 0.3829},
                4, 4);

        //System.out.println("A=");
        //System.out.println(A);

        //System.out.println("U=");
        //System.out.println(U);
        
        //System.out.println("correctU=");
        //System.out.println(correctU);
        
        assertTrue(U.closeTo(correctU, 0.01));
    }
    
    
    @Test
    public void testLUDecomposition4() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix L = A.getL();
        Matrix U = A.getR();
        Matrix P = A.getP();
        //System.out.println("A=");
        //System.out.println(A);
        //System.out.println("P=");
        //System.out.println(P);
        //System.out.println("L=");
        //System.out.println(L);
        //System.out.println("U=");
        //System.out.println(U);
        //System.out.println("testLUDecomposition4");
        //System.out.println("PA=");
        Matrix PA = P.times(A);
        //System.out.println(PA);
        //System.out.println("LU=");
        Matrix LU = L.times(U);
        //System.out.println(LU);
        
        assertTrue(PA.closeTo(LU, 0.5));
    }
    
    @Test
    public void testEliminateRow1() {
        Matrix A = new Matrix(new Double[] {
                1.0, 2.0, 3.0,
                2.0, 4.0, 6.0,
                0.0, 1.0, 2.0
        }, 3, 3);
        Matrix B = new Matrix(new Double[] {
                1.0, 2.0, 3.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 2.0
        }, 3, 3);
        
        assertTrue(A.eliminateRow(0, 1, 2).closeTo(B, 0.01));
    }
    
    @Test
    public void testRowEchelonForm5() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix P = A.getP();
        Matrix correctP = new Matrix(new double[] {
                0.0, 0.0, 0.0, 1.0,
                1.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 1.0, 0.0, 0.0
        }, 4, 4);
        //System.out.println("A=");
        //System.out.println(A);
        //System.out.println("P=");
        //System.out.println(P);
        //System.out.println("correct P=");
        //System.out.println(correctP);
        
        assertTrue(P.closeTo(correctP, 0.01));
    }
    
    @Test
    public void testRowEchelonForm6() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix correctU = new Matrix(new double[] {
                36.0, 98.000, 276.000, 794.0000,
                0.0, -4.8889, -16.667, -52.2222,
                0.0, 0.00000, -2.1364, -10.2273,
                0.0, 0.00000, 0.00000, 0.38298
        }, 4, 4);
        //System.out.println("A=");
        //System.out.println(A);
        Matrix U = A.getR();
        //System.out.println("U=");
        //System.out.println(U);
        //System.out.println("correct U=");
        //System.out.println(correctU);
        
        assertTrue(U.closeTo(correctU, 0.01));
    }
    
    @Test
    public void testRowEchelonForm7() {
        Matrix A = new Matrix(new double[] {
                4.0, 6.0, 14.0, 36.0, 
                6.0, 14.0, 36.0, 98.0,
                14.0, 36.0, 98.0, 276.0,
                36.0, 98.0, 276.0, 794.0},
                4, 4);
        Matrix P = A.getP();
        Matrix correctPA = new Matrix(new double[] {
                36.0, 98.000, 276.000, 794.0000,
                4.0, 6.0, 14.0, 36.0,
                14.0, 36.0, 98.0, 276.0,
                6.0, 14.0, 36.0, 98.0
        }, 4, 4);
        //System.out.println("A=");
        //System.out.println(A);
        Matrix PA = P.times(A);
        //System.out.println("PA=");
        //System.out.println(PA);
        //System.out.println("correctPA=");
        //System.out.println(correctPA);
        
        assertTrue(PA.closeTo(correctPA, 0.01));
    }
    
    
    
    

}

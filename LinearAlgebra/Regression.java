package LinearAlgebra;

import java.util.List;
import java.util.Random;

public class Regression {
    
    public static double[] polynomialRegression(List<Double> xCoordinates, 
            List<Double> yCoordinates, int degree) {
        
        
        
        // we create a matrix with one row for each xCoordinates and
        // one column for each power of x from 0 to degree
        
        double[] coefficientMatrix = new double[xCoordinates.size() * (degree + 1)];
        
        int index = 0;
        for (Double x : xCoordinates) {
            double v = 1.0;
            for (int i = 0; i <= degree; i ++) {
                coefficientMatrix[index] = v;
                v *= x;
                index++;
            }
        }
        
        Matrix X = new Matrix(coefficientMatrix, xCoordinates.size(), degree + 1);

        
        // we create a matrix containing the corresponding y-values
        Matrix Y = new Matrix(yCoordinates.toArray(new Double[yCoordinates.size()]), 
                yCoordinates.size(), 1);
        

        // now find the solution c of X^T X c = Y, and that will give us
        // our coefficients
        
        Matrix C = X.pinv().solve(X.transpose().times(Y));
        
        double[] coeffs = C.toDoubleArray();
        
        return coeffs;
    }
    

}

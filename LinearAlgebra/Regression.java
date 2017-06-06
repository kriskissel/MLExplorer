package LinearAlgebra;

import java.util.List;
import java.util.Random;

public class Regression {
    
    /**
     * 
     * @param xCoordinates list of x values
     * @param yCoordinates list of y values
     * @param degree degree of polynomial to fit
     * @param l2_coefficient L2-regularization coefficient
     * @return polynomial p(x) of required degree that minimizes the
     * sum of (p(x)-y)^2 over each pair (x,y)
     */
    public static double[] polynomialRegression(List<Double> xCoordinates, 
            List<Double> yCoordinates, int degree, double l2_coefficient) {
        
        
        
        // we create a matrix with one row for each xCoordinates and
        // one column for each power of x from 0 to degree
        
        double[] coefficientMatrix = new double[xCoordinates.size() * (degree + 1)];
        
        int index = 0;
        for (Double x : xCoordinates) {
            double v = 1.0;
            for (int i = 0; i <= degree; i++) {
                coefficientMatrix[index] = v;
                v *= x;
                index++;
            }
        }
        
        Matrix X = new Matrix(coefficientMatrix, xCoordinates.size(), degree + 1);

        // we create a matrix containing the corresponding y-values
        Matrix Y = new Matrix(yCoordinates.toArray(new Double[yCoordinates.size()]), 
                yCoordinates.size(), 1);

        // find the solution c of X^T X c = Y, and that will give us
        // our coefficients
        Matrix C = (X.transpose().times(X)).plus(Matrix.identity(X.columns() 
                ).times(l2_coefficient)).solve(X.transpose().times(Y));
        
        double[] coeffs = C.toDoubleArray();
        
        return coeffs;
    }
    
    
    /**
     * 
     * @param xCoordinates list of x values
     * @param yCoordinates list of y values
     * @param degree degree of polynomial to fit
     * @return polynomial p(x) of required degree that minimizes the
     * sum of (p(x)-y)^2 over each pair (x,y)
     */
    public static double[] polynomialRegression(List<Double> xCoordinates, 
            List<Double> yCoordinates, int degree) {
        return polynomialRegression(xCoordinates, yCoordinates, degree, 0);
    }
    
    /**
     * 
     * @param xCoordinates list of x values
     * @param yCoordinates list of y values
     * @param maxDegree maximum degree of polynomial to fit
     * @return polynomial p(x) of degree less than or equal to
     * maxDegree that minimizes the
     * sum of (p(x)-y)^2 over each pair (x,y)
     */
    public static double[] polynomialRegressionAutoReduceDegree(List<Double> xCoordinates, 
            List<Double> yCoordinates, int maxDegree, double l2_coefficient) {
        
        double[] coeffs = new double[maxDegree + 1];
        
        for (int degree = maxDegree; degree >= 1; degree--) {
            try {
                coeffs =  polynomialRegression(xCoordinates, yCoordinates, 
                        degree, l2_coefficient);
                break;
            } catch (Exception e) {
                System.out.println("Unable to peform degree " + degree + 
                        " polynomial regression on given data");
            }
        }
        return coeffs; 
    }
    
    /**
     * 
     * @param xCoordinates list of x values
     * @param yCoordinates list of y values
     * @param maxDegree maximum degree of polynomial to fit
     * @return polynomial p(x) of degree less than or equal to
     * maxDegree that minimizes the
     * sum of (p(x)-y)^2 over each pair (x,y)
     */
    public static double[] polynomialRegressionAutoReduceDegree(List<Double> xCoordinates, 
            List<Double> yCoordinates, int maxDegree) {
        return polynomialRegressionAutoReduceDegree(xCoordinates, yCoordinates,
                maxDegree, 0);
    }
    
    
    public static double squareError(double[] coeffs, List<Double> xCoordinates, 
            List<Double> yCoordinates) {
        int n = xCoordinates.size();
        double var = 0.0;
        for (int i = 0; i < n; i++) {
            double x = xCoordinates.get(i);
            double v = coeffs[0];
            double xPower = 1.0;
            for (int p = 1; p < coeffs.length; p++){
                xPower *= x;
                v += xPower * coeffs[p];
            }
            var += (v - yCoordinates.get(i))*(v - yCoordinates.get(i));
        }
        return var / ((float) n);
    }

}

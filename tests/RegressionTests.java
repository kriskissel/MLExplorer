package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import LinearAlgebra.Regression;

public class RegressionTests {
    
    @Test
    public void testLinearRegressionOnTwoDataPoints1() {
        List<Double> xCoordinates = new ArrayList<Double>();
        xCoordinates.add(0.0);
        xCoordinates.add(2.0);
        List<Double> yCoordinates = new ArrayList<Double>();
        yCoordinates.add(0.0);
        yCoordinates.add(4.0);
        double[] coeffs = Regression.polynomialRegression(xCoordinates, yCoordinates, 1);
        assertTrue(coeffs[0] < 0.01 && coeffs[0] > -0.01); // y-intercept should be 0
        assertTrue(coeffs[1] < 2.01 && coeffs[1] > 1.99); // slope should be 2
    }
    
    @Test
    public void testQuadraticRegressionOnThreeDataPoints1() {
        // three data points on graph of x -> 2 x^2 - x + 0.5
        // just check to see whether method returns a value without error
        List<Double> xCoordinates = new ArrayList<Double>();
        xCoordinates.add(0.0);
        xCoordinates.add(1.0);
        xCoordinates.add(2.0);
        List<Double> yCoordinates = new ArrayList<Double>();
        yCoordinates.add(0.5);
        yCoordinates.add(1.5);
        yCoordinates.add(6.5);
        double[] coeffs = Regression.polynomialRegression(xCoordinates, yCoordinates, 2);
    }
    
    @Test
    public void testQuadraticRegressionOnThreeDataPoints2() {
        // three data points on graph of x -> 2 x^2 - x + 0.5
        List<Double> xCoordinates = new ArrayList<Double>();
        xCoordinates.add(0.0);
        xCoordinates.add(1.0);
        xCoordinates.add(2.0);
        List<Double> yCoordinates = new ArrayList<Double>();
        yCoordinates.add(0.5);
        yCoordinates.add(1.5);
        yCoordinates.add(6.5);
        double[] coeffs = Regression.polynomialRegression(xCoordinates, yCoordinates, 2);
        //for (double d : coeffs) { System.out.println(d); }
        assertTrue(coeffs[0] < 0.51 && coeffs[0] > 0.49); // coeffs[0] should be 0.5
        assertTrue(coeffs[1] < -0.99 && coeffs[1] > -1.01); // coeffs[1] shoudl be -1
        assertTrue(coeffs[2] < 2.01 && coeffs[2] > 1.99); // coeffs[2] should be 2
    }
    
    @Test
    public void testCubicRegressionOnFourDataPoints1() {
        // three data points on graph of x -> -x^3 + 2 x^2 - x + 1
        List<Double> xCoordinates = new ArrayList<Double>();
        xCoordinates.add(0.0);
        xCoordinates.add(1.0);
        xCoordinates.add(2.0);
        xCoordinates.add(3.0);
        List<Double> yCoordinates = new ArrayList<Double>();
        yCoordinates.add(1.0);
        yCoordinates.add(1.0);
        yCoordinates.add(-1.0);
        yCoordinates.add(-11.0);
        double[] coeffs = Regression.polynomialRegression(xCoordinates, yCoordinates, 3);
        //for (double d : coeffs) { System.out.println(d); }
        assertTrue(coeffs[0] < 1.01 && coeffs[0] > 0.99); // coeffs[0] should be 1
        assertTrue(coeffs[1] < -0.99 && coeffs[1] > -1.01); // coeffs[1] shoudl be -1
        assertTrue(coeffs[2] < 2.01 && coeffs[2] > 1.99); // coeffs[2] should be 2
        assertTrue(coeffs[3] < -0.99 && coeffs[3] > -1.01); // coeffs[3] should be -1
    }

}

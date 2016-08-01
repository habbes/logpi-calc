package com.manuscript.logpicalc.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class FunctionList {
	
	private static HashMap<String, FunctionMaker> map = initMap();
	
	public static final Random randgen = new Random();
	
	/**
	 * Answer of previous calculation.
	 */
	public static double ans = 0;
	
	/**
	 * Default error tolerance (used for functions like almost).
	 */
	public static double defaultTolerance = 0.0000000001d;
	
	//caches for lengthy alogrithms
	private static HashMap<Long, Double> fiboMap = new HashMap<Long, Double>();
	
	private static HashMap<Long, Double> factMap = new HashMap<Long, Double>();
	
	private static HashMap<Long, Double> triangMap = new HashMap<Long, Double>();
	
	/**
	 * Array of available functions.
	 */
	public static String list[] = {
		
		//constants
		"pi",
		"e",
		
		//basic
		"abs",
		"mod",
		
		"sqrt",
		"cbrt",
		"root",
		"log",
		"ln",
		
		//trig functions
		"torad",
		"todeg",
		"sin",
		"cos",
		"tan",
		"asin",
		"acos",
		"atan",
		"atan2",
		"csc",
		"sec",
		"cot",		
		"sinh",
		"cosh",
		"tanh",
		"hypot",
		
		//statistics
		"sum",
		"product",
		"mean",
		"mode",
		"max",
		"min",
		
		//round
		"round",
		"ceil",
		"floor",
		
		//combinations/permutations
		"factorial",
		"npr",
		"ncr",
		
		//logic
		"eq",
		"gt",
		"lt",
		"gteq",
		"lteq",
		"almost",
		"or",
		"and",
		"not",
		"xor",
		
		//series
		"fibonacci",
		"triangular",
		
		//rand
		"rand",
		"randint",
		"randbit",
		
		};
	
	//HELPER FUNCTIONS
	private static double factorial(long n){
		if(n <= 1) return 1;
		if(factMap.containsKey(n)){
			return factMap.get(n);
		}
		double r = n * factorial(n - 1);
		factMap.put(n, r);
		return r;
	}
	
	private static double fibonacci(long n){
		if(n <= 1) return 1;
		if(fiboMap.containsKey(n)){
			return fiboMap.get(n);
		}
		double r = fibonacci(n - 1) + fibonacci(n - 2);
		fiboMap.put(n, r);
		return r;
	}
	
	private static double triangular(long n){
		if(n <= 1) return 1;
		if(triangMap.containsKey(n)){
			return triangMap.get(n);
		}
		double r = n + triangular(n - 1);
		triangMap.put(n, r);
		return r;
	}
	
	private static double max(List<Double> params){
		double max = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < params.size(); i++){
			if(params.get(i) > max){
				max = params.get(i);
			}
		}
		return max;
	}
	
	private static double min(List<Double> params){
		double min = Double.POSITIVE_INFINITY;
		for(int i = 0; i < params.size(); i++){
			if(params.get(i) < min){
				min = params.get(i);
			}
		}
		return min;
	}
	
	private static boolean isInt(double n){
		return n == (double) ((long) n);
	}
	
	private static double mod(double n, double m){
		if(isInt(n) && isInt(m)){
			return n % m;
		}
		return Math.IEEEremainder(n, m);
	}
	
	//HARD CODE KNOWN VALUES FOR TRIG FUNCTIONS
	
	private static double sin(double n){
		double pi = Math.PI;
		double ang = mod(n, 2 * Math.PI);
		if(ang == 0)return 0;
		if(ang == pi/6)	return 0.5;
		if(ang == pi/2) return 1;
		if(ang == pi - pi/6) return 0.5;
		if(ang == pi) return 0;
		if(ang == pi + pi/6) return -0.5;
		if(ang == pi + pi/2) return -1;
		if(ang == 2*pi - pi/6) return -0.5;
		
		return Math.sin(n);
	}
	
	private static double cos(double n){
		double pi = Math.PI;
		double ang = mod(n, 2 * Math.PI);
		if(ang == 0) return 1;
		if(ang == pi/3)	return 0.5;
		if(ang == pi/2) return 0;
		if(ang == pi - pi/3) return -0.5;
		if(ang == pi) return -1;
		if(ang == pi + pi/3) return -0.5;
		if(ang == pi + pi/2) return 0;
		if(ang == 2*pi - pi/3) return 0.5;
		
		return Math.cos(n);
	}
	
	private static double tan(double n){
		double pi = Math.PI;
		double ang = mod(n, 2 * Math.PI);
		if(ang == 0) return 0;
		if(ang == pi/4)	return 1;
		//if(ang == pi/2) return Double.POSITIVE_INFINITY;
		if(ang == pi - pi/4) return -1;
		if(ang == pi) return 0;
		if(ang == pi + pi/4) return 1;
		//if(ang == pi + pi/2) return Double.NEGATIVE_INFINITY;
		if(ang == 2*pi - pi/4) return -1;
		
		return Math.cos(n);
	}
	
	
	//END HELPES
	
	/**
	 * Creates functions.
	 * @author Habbes
	 *
	 */
	static class  FunctionMaker{
		String name;
		FunctionEvaluator evaluator;
		int maxparams = Function.INFINITE_PARAMS;
		
		public FunctionMaker(String n, int max_p, FunctionEvaluator ev){
			name = n;
			maxparams = max_p;
			evaluator = ev;
		}
		
		public FunctionMaker(String n, FunctionEvaluator ev){
			this(n, Function.INFINITE_PARAMS, ev);
		}
		
		public Function make(){
			return new Function(name, maxparams, evaluator);
		}
	}
	
	/**
	 * Adds implementation of a function with default max number of parameters.
	 * @param m map where to add the function
	 * @param name name of the function
	 * @param evaluator callback that evaluates the function
	 */
	private static void addFunction(HashMap<String, FunctionMaker> m, String name, FunctionEvaluator evaluator){
		addFunction(m,  name, Function.INFINITE_PARAMS, evaluator);
	}
	
	/**
	 * Adds implementation of a function.
	 * @param m map where to add the function
	 * @param name name of the function
	 * @param maxparams maximum number of parameters the function can take
	 * @param evaluator callback that evaluates the function
	 */
	private static void addFunction(HashMap<String, FunctionMaker> m, String name, int maxparams, FunctionEvaluator evaluator){
		//TODO: Why use FunctionMaker and note add Function directly?
		m.put(name, new FunctionMaker(name, maxparams, evaluator));
	}
	
	
	/**
	 * Implements all functions and adds them to the main map
	 */
	private static HashMap<String, FunctionMaker> initMap(){
		HashMap<String, FunctionMaker> m = new HashMap<String, FunctionMaker>();
		
		addFunction(m, "ans", 0, new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				return ans;
			}
		});
		
		
		
		addFunction(m, "pi", 0, new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.PI;
			}
		});
		
		addFunction(m, "e", 0, new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.E;
			}
		});
		
		addFunction(m, "mod", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				return mod(params.get(0), params.get(1));
			}
		});
		
		addFunction(m, "abs", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				return Math.abs(params.get(0));
			}
		});
		
		addFunction(m, "gt", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				return (n1 > n2)? 1 : 0;
			}
		});
		
		addFunction(m, "lt", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				return (n1 < n2)? 1 : 0;
			}
		});
		
		addFunction(m, "eq", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				return (n1 == n2)? 1 : 0;
			}
		});
		
		addFunction(m, "gteq", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				return (n1 >= n2)? 1 : 0;
			}
		});
		
		addFunction(m, "lteq", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				return (n1 <= n2)? 1 : 0;
			}
		});
		
		addFunction(m, "or", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				for(double d : params){
					if(d != 0d){
						return 1;
					}
				}
				return 0;
			}
		});
		
		addFunction(m, "and", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				for(double d : params){
					if(d == 0d){
						return 0;
					}
				}
				return 1;
			}
		});
		
		addFunction(m, "not", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				return (params.get(0) == 0)? 1 :  0;
			}
		});
		
		addFunction(m, "xor", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				long res = 0;
				for(double d : params){
					if(d != 0){
						res += 1;
					}
				}
				return res % 2;
			}
		});
		
		addFunction(m, "almost", new FunctionEvaluator(){
			@Override
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0).longValue();
				double n2 = params.get(1).longValue();
				double tolerance = defaultTolerance;
				if(params.size() > 2){
					tolerance = params.get(2);
				}
				return (Math.abs(n1 - n2) <= tolerance)? 1 : 0;
			}
		});
		
		addFunction(m, "sqrt", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.sqrt(params.get(0));
			}
		});
		
		addFunction(m, "cbrt", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.cbrt(params.get(0));
			}
		});
		
		addFunction(m, "root", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				double n = params.get(0);
				double r = params.get(1);
				return Math.pow(n, 1/r);
			}
		});
		
		addFunction(m, "log",new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				if(params.size() == 1){
					return Math.log10(params.get(0));
				}
				return Math.log(params.get(0)) / Math.log(params.get(1));
			}
		});
		
		addFunction(m, "ln", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.log(params.get(0));
			}
		});
		
		addFunction(m, "factorial", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return factorial(params.get(0).longValue());
			}
		});
		
		addFunction(m, "fibonacci", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return fibonacci(params.get(0).longValue());
			}
		});
		
		addFunction(m, "triangular", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return triangular(params.get(0).longValue());
			}
		});
		
		addFunction(m, "sin", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return sin(params.get(0));
			}
		});
		
		addFunction(m, "cos", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return cos(params.get(0));
			}
		});
		
		addFunction(m, "tan", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return tan(params.get(0));
			}
		});
		
		addFunction(m, "asin", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.asin(params.get(0));
			}
		});
		
		addFunction(m, "acos", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.acos(params.get(0));
			}
		});
		
		addFunction(m, "atan", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.atan(params.get(0));
			}
		});
		
		addFunction(m, "atan2", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.atan2(params.get(0), params.get(1));
			}
		});
		
		addFunction(m, "sinh", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.sinh(params.get(0));
			}
		});
		
		addFunction(m, "cosh", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.cosh(params.get(0));
			}
		});
		
		addFunction(m, "tanh", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.tanh(params.get(0));
			}
		});
		
		addFunction(m, "cot", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return 1/Math.tan(params.get(0));
			}
		});
		
		addFunction(m, "csc", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return 1/Math.sin(params.get(0));
			}
		});
		
		addFunction(m, "sec", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return 1/Math.cos(params.get(0));
			}
		});
		
		addFunction(m, "torad", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.toRadians(params.get(0));
			}
		});
		
		addFunction(m, "todeg", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.toDegrees(params.get(0));
			}
		});
		
		addFunction(m, "hypot", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				double n1 = params.get(0);
				double n2 = params.get(1);
				return Math.sqrt(Math.pow(n1, 2) + Math.pow(n2, 2));
			}
		});
		
		addFunction(m, "sum", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				double sum = 0;
				for(Double d : params){
					sum += d;
				}
				return sum;
			}
		});
		
		addFunction(m, "product", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				double p = 1;
				for(Double d : params){
					p *= d;
				}
				return p;
			}
		});
		
		addFunction(m, "mean", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				double sum = 0;
				for(Double d : params){
					sum += d;
				}
				double mean = 0;
				if(params.size() > 0){
					mean = sum / params.size();
				}
				return mean;
			}
		});
		
		
		
		addFunction(m, "mode", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				
				HashMap<Double, Integer> freqs = new HashMap<Double, Integer>();
				double cur;
				double[] maxFreq = {0, 0};
				for(int i = 0; i < params.size(); i++){
					cur = params.get(i);
					int f;
					if(freqs.containsKey(cur)){
						f = freqs.get(cur);
						f++;
					}
					else {
						f = 1;
					}
					freqs.put(cur, f);
					if(f > maxFreq[1]){
						maxFreq[0] = cur;
						maxFreq[1] = f;
					}
					
				}
				return maxFreq[0];
			}
		});
		
		addFunction(m, "max", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return max(params);
			}
		});
	
		addFunction(m, "min", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return min(params);
			}
		});
		
		addFunction(m, "npr", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				long n = params.get(0).longValue();
				long r = params.get(1).longValue();
				return factorial(n)/factorial(n - r);
			}
		});
		
		addFunction(m, "ncr", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				long n = params.get(0).longValue();
				long r = params.get(1).longValue();
				return factorial(n)/(factorial(r) * factorial(n - r));
			}
		});
		
		addFunction(m, "randbit", 0, new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				boolean b =  randgen.nextBoolean();
				if(b) return 1;
				return 0;
			}
		});
		
		addFunction(m, "rand", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				if(params.size() == 0){
					return randgen.nextDouble();
				}
				
				if(params.size() == 1){
					return randgen.nextDouble() * params.get(0);
				}
				
				double max = params.get(0);
				double min = params.get(1);
				if(min > max){
					double temp = min;
					min = max;
					max = temp;
				}
				return (max - min) + (randgen.nextDouble() * min);
			}
		});
		
		addFunction(m, "randint", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				if(params.size() == 0){
					return randgen.nextInt();
				}
				
				if(params.size() == 1){
					return randgen.nextInt(params.get(0).intValue());
				}
				
				int max = params.get(0).intValue();
				int min = params.get(1).intValue();
				if(min > max){
					int temp = min;
					min = max;
					max = temp;
				}
				return (max - min) + randgen.nextInt(min);
			}
		});
		
		addFunction(m, "round", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.round(params.get(0));
			}
		});
		
		addFunction(m, "ceil", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.ceil(params.get(0));
			}
		});
		
		addFunction(m, "floor", new FunctionEvaluator(){
			public double evaluate(ArrayList<Double> params) {
				return Math.floor(params.get(0));
			}
		});
		
		return m;
	}
	
	/**
	 * Gets the list of functions sorted by name.
	 * @return sorted list of functions
	 */
	public static String[] getSortedList(){
		Arrays.sort(list);
		return list;
	}
	
	/**
	 * Gets the function with the specified name.
	 * @param name name of function
	 * @return function
	 */
	public static Function getFunction(String name){
		return map.get(name).make();		
	}

	
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

public class Countdown {
	/* Stores the solution */
	public static int path[];

	/* Current depth of recursion */
	public static int depth;

	/*
	 * All data is stored here so that it doesn't have to be passed down during
	 * recursion - saves time
	 */
	public static int numbers[];

	/* Counters of how many operations we have done */
	public static int operations;

	public static int maxDepth;

	public static boolean solved;

	public static String solution;

	public static void recurse(int base, int no) {
		int a, b, c, d, nbase;
		++depth;
		if (depth == 2) {
			/* Give an indication that we're still alive */
			// System.out.print(".");
		}
		/*
		 * Figure out which part of the numbers array we're going to be
		 * addressing
		 */
		nbase = base + no + 1;
		/*
		 * Our last calculation came up with a solution, so output the path so
		 * far and stop
		 */
		if (numbers[0 + base] == numbers[1 + base]) {
			solution = "Solved: \n";
			for (a = 0; a < depth - 1; ++a) {
				solution += "" + path[a * 5] + "";
				if (path[a * 5 + 1] == 0)
					solution += " = ";
				if (path[a * 5 + 1] == 1)
					solution += " * ";
				if (path[a * 5 + 1] == 2)
					solution += " + ";
				if (path[a * 5 + 1] == 3)
					solution += " - ";
				if (path[a * 5 + 1] == 4)
					solution += " / ";
				solution += "" + path[a * 5 + 2] + "";
				if (path[a * 5 + 3] == 0)
					solution += " = ";
				if (path[a * 5 + 3] == 1)
					solution += " * ";
				if (path[a * 5 + 3] == 2)
					solution += " + ";
				if (path[a * 5 + 3] == 3)
					solution += " - ";
				if (path[a * 5 + 3] == 4)
					solution += " / ";
				solution += "" + path[a * 5 + 4] + "\n";
			}
			// System.out.println("operations: "+operations);
			solved = true;
			return;
		} else if (no == maxDepth) {
			/*
			 * We're at the bottom of the recursion and haven't found a
			 * solution, so do nothing
			 */
		} else {
			/*
			 * We're at a higher level than the bottom of the recursion, and
			 * haven't found a solution, so iterate through the possible number
			 * pairs and operations
			 */
			for (a = 1; a < no; ++a) {
				for (b = 1; b < no; ++b) {
					if (a > b) {
						numbers[0 + nbase] = numbers[0 + nbase];
						c = 1;
						numbers[c + nbase] = numbers[a + base] * numbers[b + base];
						++operations;
						++c;
						for (d = 1; d < no; ++d) {
							if (d != a && d != b) {
								numbers[c + nbase] = numbers[d + base];
								++c;
							}
						}
						path[(depth - 1) * 5] = numbers[a + base];
						path[(depth - 1) * 5 + 1] = 1;
						path[(depth - 1) * 5 + 2] = numbers[b + base];
						path[(depth - 1) * 5 + 3] = 0;
						path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
						recurse(nbase, no - 1);
						if (solved) {
							return;
						}
						numbers[1 + nbase] = numbers[a + base] + numbers[b + base];
						++operations;
						path[(depth - 1) * 5 + 1] = 2;
						path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
						recurse(nbase, no - 1);
						if (solved) {
							return;
						}
						numbers[1 + nbase] = numbers[a + base] - numbers[b + base];
						++operations;
						if (numbers[1 + nbase] > 0) {
							path[(depth - 1) * 5 + 1] = 3;
							path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
							recurse(nbase, no - 1);
							if (solved) {
								return;
							}
						}
						if ((numbers[b + base] != 0) && (numbers[a + base] % numbers[b + base] == 0)) {
							numbers[1 + nbase] = numbers[a + base] / numbers[b + base];
							++operations;
							path[(depth - 1) * 5 + 1] = 4;
							path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
							recurse(nbase, no - 1);
							if (solved) {
								return;
							}
						}
					} else if (b > a) {
						/*
						 * Only do division and subtraction - multiplication and
						 * addition will be done with this number pair ordered
						 * the other way around, and will give the same result
						 */
						numbers[0 + nbase] = numbers[0 + base];
						c = 1;
						numbers[c + nbase] = numbers[a + base] - numbers[b + base];
						++operations;
						++c;
						for (d = 1; d < no; ++d) {
							if (d != a && d != b) {
								numbers[c + nbase] = numbers[d + base];
								++c;
							}
						}
						if (numbers[1 + nbase] > 0) {
							path[(depth - 1) * 5] = numbers[a + base];
							path[(depth - 1) * 5 + 1] = 3;
							path[(depth - 1) * 5 + 2] = numbers[b + base];
							path[(depth - 1) * 5 + 3] = 0;
							path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
							recurse(nbase, no - 1);
							if (solved) {
								return;
							}
						}
						if ((numbers[b + base] != 0) && (numbers[a + base] % numbers[b + base] == 0)) {
							numbers[1 + nbase] = numbers[a + base] / numbers[b + base];
							path[(depth - 1) * 5] = numbers[a + base];
							path[(depth - 1) * 5 + 1] = 4;
							path[(depth - 1) * 5 + 2] = numbers[b + base];
							path[(depth - 1) * 5 + 3] = 0;
							path[(depth - 1) * 5 + 4] = numbers[1 + nbase];
							recurse(nbase, no - 1);
							if (solved) {
								return;
							}
						}
					}
				}
			}
		}

		--depth;
	}
	public static void main(String[] args) {
		BufferedReader br = null;
		String line = "";
		if (!ArrayUtils.isEmpty(args)) {
			File inputFile = new File(args[0]);
			try{
				br = new BufferedReader(new FileReader(inputFile));
				while ((line = br.readLine()) != null) {
					String[] strNumbers = line.split(",");
					solve(strNumbers);
				}
			}
			catch(FileNotFoundException e) {
				System.out.println("File not found.");
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//if ()
	}
	public static void solve(String args[]) {
		int a, no;

		no = args.length;
		solved = false;
		solution = "No solution found.";

		if (no == 0) {
			System.out.println("Usage:");
			System.out.println("java countdown TARGET SEED1 SEED2 ...");
		} else {
			try {
				numbers = new int[no * (no - 1)];
				path = new int[(no - 1) * 5];
				depth = 0;
				operations = 0;
				for (a = 0; a < args.length; ++a) {
					if (args[a].equals("")) {
						numbers[a] = 0;
					} else {
						numbers[a] = Integer.parseInt(args[a]);
					}

					if (a > 0 && numbers[a] == numbers[0]) {
						solution = "Input number is answer";
						solved = true;
					}
				}

				for (maxDepth = no - 1; maxDepth > 1 && !solved; --maxDepth) {
					recurse(0, no);
				}
			} catch (NumberFormatException e) {
				solution = "Invalid number entered:  " + e.getMessage();
			}

		}

		System.out.println(solution);
	}

}
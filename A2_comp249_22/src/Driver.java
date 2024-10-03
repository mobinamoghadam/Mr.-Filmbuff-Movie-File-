// -----------------------------------------------------
// Assignment #2
// COMP 249
// Written by: Mobina Gerayely Moghadam 40258626 , Rojan Nessari 40255637
// -----------------------------------------------------
/**
 * Driver class is a Driver program for processing movie data.
 * Driver class implements methods for reading, validating, categorizing, and navigating movie records.
 * The program is divided into three parts: 
 * part 1 partition all valid movie records into new genre based1text files.
 * part 2 serializes movie data.
 * part 3 deserialize movie data and populates the all_movies array.
 * 
 * @author Mobina Gerayely Moghadam 40258626 , Rojan Nessari 40255637
 * @see Movie
 * @see java.io.*
 * @see SemanticErrors
 * @see SyntaxErrors
 */
import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Driver {
	static int count;
	static Movie[][] all_movies = new Movie[17][];
	static String[] genres = { "musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror",
			"action", "documentary", "fantasy", "mystery", "sci-fi", "family", "romance", "thriller", "western" };

	/**
     * Processes part 1 of movie data.
     * Reads the part 1 manifest file, validates movie records, categorizes movies by genre,
     * and writes bad movie records to a file.
     *
     * @param manifestFileName The name of the part 1 manifest file.
     */
	public static void do_part1(String fileNamemanifestFileName) {
		Scanner inputFileScanner = null;
		Scanner movieInputFileReader = null;
		PrintWriter badMovie = null;
		PrintWriter[] genreWriter = null;
		PrintWriter part2ManifestWriter = null;
		String line = null;
		int year;
		int duration;
		double score;
		String title;
		String genre = null;
		String rating;
		String director;
		String actor1;
		String actor2;
		String actor3;

		// Reading part1 manifest file
		try {
			inputFileScanner = new Scanner(new FileInputStream("part1 manifest.txt"));
			while (inputFileScanner.hasNextLine()) {
				inputFileScanner.nextLine();
				count++;
			}
			inputFileScanner.close();
			// writing bad movie files
			// writing genre writer
			// writing the Manifest part2 file containing genres names
			badMovie = new PrintWriter(new FileOutputStream("bad movie records.txt"));
			genreWriter = new PrintWriter[17];
			part2ManifestWriter = new PrintWriter(new FileOutputStream("Part2_Manifest.txt"));

			String[] genres = { "musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror",
					"action", "documentary", "fantasy", "mystery", "sci-fi", "family", "romance", "thriller",
					"western" };

			for (int i = 0; i < 17; i++) {
				genreWriter[i] = new PrintWriter(new FileOutputStream(genres[i] + ".csv"));
				part2ManifestWriter.println(genres[i] + ".csv");
			}

			inputFileScanner = new Scanner(new FileInputStream("part1 manifest.txt"));
			for (int i = 0; i < count; i++) {
				String fileName = inputFileScanner.nextLine();
				movieInputFileReader = new Scanner(new FileInputStream(fileName));
				while (movieInputFileReader.hasNextLine()) {
					line = movieInputFileReader.nextLine();
					String[] partBasedOnQuotes = line.split("\"");
					try {
						if (partBasedOnQuotes.length != 3 && partBasedOnQuotes.length != 1) {
							throw new SyntaxErrors.MissingQuotesException("missing the end quote in a quoted field");
						}

						if (partBasedOnQuotes.length == 1) {
							String[] partByCommas = line.split(",");

							if (partByCommas.length > 10) {
								throw new SyntaxErrors.ExcessFieldsException("Sysntax Error: too many filed! ");
							} else if (partByCommas.length < 10) {
								throw new SyntaxErrors.MissingFieldsException("Syntax Error: few Fields ");
							} else {

								try {
									if (partByCommas[0].isEmpty())
										throw new SemanticErrors.MissingPart("year in missing ");
									else {
										year = Integer.parseInt(partByCommas[0]);
										validYear(year);

									}
									if (partByCommas[1].trim().isEmpty())
										throw new SemanticErrors.MissingPart("title in missing ");
									else {
										title = partByCommas[1];

									}
									if (partByCommas[2].trim().isEmpty())
										throw new SemanticErrors.MissingPart("duration in missing ");
									else {
										duration = Integer.parseInt(partByCommas[2]);
										validDuration(duration);

									}

									if (partByCommas[4].trim().isEmpty())
										throw new SemanticErrors.MissingPart("rating in missing ");
									else {
										rating = partByCommas[4];
										validRating(rating);

									}
									if (partByCommas[5].trim().isEmpty())
										throw new SemanticErrors.MissingPart("score in missing ");
									else {
										score = Double.parseDouble(partByCommas[5]);
										validScore(score);

									}
									if (partByCommas[6].trim().isEmpty() || partByCommas[7].trim().isEmpty()
											|| partByCommas[8].trim().isEmpty() || partByCommas[9].trim().isEmpty())
										throw new SemanticErrors.MissingPart("names are misssing ");
									else {
										director = partByCommas[6];
										actor1 = partByCommas[7];
										actor2 = partByCommas[8];
										actor3 = partByCommas[9];

									}
									if (partByCommas[3].trim().isEmpty())
										throw new SemanticErrors.MissingPart("genre in missing ");
									else {
										genre = partByCommas[3];
										validGenre(genre);
										int genreIndex = getGenreIndex(genre.trim());
										genreWriter[genreIndex].println(line);
									}
								} catch (SemanticErrors.MissingPart | SemanticErrors.InvalidPart
										| NumberFormatException e) {
									badMovie.println("---------------------");
									badMovie.println(line);
									badMovie.println(e.getMessage());
								}
							}
						} else {
							String[] partByComma = partBasedOnQuotes[2].substring(1).split(",");

							if (partByComma.length > 8)
								throw new SyntaxErrors.ExcessFieldsException(" Sysntax Eroor : too many filed! ");
							else if (partByComma.length < 8)
								throw new SyntaxErrors.MissingFieldsException("Syntax Error: few Fields");
							else {
								try {

									String[] commas = partBasedOnQuotes[0].split(",");
									if (commas.length == 0)
										throw new SemanticErrors.MissingPart("year in missing ");
									else {
										year = Integer.parseInt(
												partBasedOnQuotes[0].substring(0, partBasedOnQuotes[0].length() - 1));
										validYear(year);
									}
									title = partBasedOnQuotes[1];
									if (title.trim().isEmpty())
										throw new SemanticErrors.MissingPart("title is missing ");
									if (partByComma[0].trim().isEmpty())
										throw new SemanticErrors.MissingPart("duration is missing ");
									else {
										duration = Integer.parseInt(partByComma[0].trim());
										validDuration(duration);
									}
									if (partByComma[2].trim().isEmpty())
										throw new SemanticErrors.MissingPart("rating is missing ");
									else {
										rating = partByComma[2];
										validRating(rating);
									}
									if (partByComma[3].trim().isEmpty())
										throw new SemanticErrors.MissingPart("score is missing ");
									else {
										score = Double.parseDouble(partByComma[3]);
										validScore(score);
									}
									if (partByComma[4].trim().isEmpty() || partByComma[5].trim().isEmpty()
											|| partByComma[6].trim().isEmpty() || partByComma[7].trim().isEmpty())
										throw new SemanticErrors.MissingPart("names are misssing ");
									else {
										director = partByComma[4];
										actor1 = partByComma[5];
										actor2 = partByComma[6];
										actor3 = partByComma[7];
									}
									if (partByComma[1].trim().isEmpty())
										throw new SemanticErrors.MissingPart("genre is missing ");
									else {
										genre = partByComma[1];
										validGenre(genre);
										int genreIndex = getGenreIndex(genre.trim());
										genreWriter[genreIndex].println(line);
									}
								} catch (SemanticErrors.MissingPart | NumberFormatException
										| SemanticErrors.InvalidPart e) {
									badMovie.println("---------------------");
									badMovie.println(line);
									badMovie.println(e.getMessage());
								}
							}
						}
					} catch (SyntaxErrors.MissingQuotesException | SyntaxErrors.MissingFieldsException
							| SyntaxErrors.ExcessFieldsException e) {
						badMovie.println("---------------------");
						badMovie.println(line);
						badMovie.println(e.getMessage());
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist!");
		} finally {
			// Close all writers
			if (badMovie != null) {
				badMovie.close();
			}
			if (part2ManifestWriter != null) {
				part2ManifestWriter.close();
			}
			if (genreWriter != null) {
				for (PrintWriter writer : genreWriter) {
					if (writer != null) {
						writer.close();
					}
				}
			}
		}
	}


    /**
     * Processes part 2 of movie data.
     * Reads part 2 manifest file, loads movies from CSV files, serializes movie data into binary files,
     * and writes file names to part 3 manifest file.
     *
     * @param manifestFileName The name of the part 2 manifest file.
     */
	public static void do_part2(String fileNamemanifestFileName) {
		Scanner part2ManifestScanner = null;
		PrintWriter part3ManifestWriter = null;
		// Reading Manifest part2File to get the list of genre films
		try {
			part2ManifestScanner = new Scanner(new FileInputStream("part2_manifest.txt"));
			part3ManifestWriter = new PrintWriter(new FileOutputStream("part3_manifest.txt"));

			while (part2ManifestScanner.hasNextLine()) {
				String csvFileName = part2ManifestScanner.nextLine();

				// Loading records from CSV into an array of Movie objects
				Movie[] movies = loadMovies(csvFileName);

				// Serializing arrays of Movie object into a binary file
				String serializedFileName = csvFileName.replace(".csv", ".ser");
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						new FileOutputStream(serializedFileName));

				objectOutputStream.writeObject(movies);
				objectOutputStream.close();

				// writing to the file Part3_manifest
				part3ManifestWriter.println(serializedFileName);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Manifest file not found: part2_manifest.txt");
		} catch (IOException e) {
			System.out.println("Error occurred during serialization: " + e.getMessage());
		} finally {
			if (part2ManifestScanner != null) {
				part2ManifestScanner.close();
			}
			if (part3ManifestWriter != null) {
				part3ManifestWriter.close();
			}
		}
	}

	/**
     * Processes part 3 of movie data.
     * Reads part 3 manifest file, deserialize movie data from binary files,
     * and populates the all_movies array.
     */
	public static void do_part3() {
		Scanner manifestScanner = null;
		ObjectInputStream ois = null;
		// Increase the size to accommodate all genres
		String fileName = "";
		try {
			manifestScanner = new Scanner(new FileInputStream("part3_manifest.txt"));
			int i = 0;

			while (manifestScanner.hasNextLine()) {
				try {
					fileName = manifestScanner.nextLine();
					int count3 = Count(fileName);
					all_movies[i] = new Movie[count3];
					ois = new ObjectInputStream(new FileInputStream(fileName));
					all_movies[i] = (Movie[]) ois.readObject();
				} catch (IOException e) {
					System.out.println("Could not open file " + fileName + " " + e.getMessage());

					all_movies[i] = new Movie[0];

				} catch (ClassNotFoundException e) {
					System.out.println("Could not find the class at " + e.getMessage());

				} catch (Exception e) {

					System.out.println("Different exception occured at creating all_movies " + e.getMessage());
				}
				i++;
			}
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (manifestScanner != null) {
				manifestScanner.close();
			}
		}			
	}

	/**
     * Displays the main menu and handles user navigation through movie genres.
     */
	public static void Menu() {
		int navigate = 0;
		int initial = 0;
		int initial2 = 0;
		while (true) {

			int count = all_movies[navigate].length;
			Scanner kb = new Scanner(System.in);
			System.out.println("-----------------------------");
			System.out.println("       Main Menu     ");
			System.out.println("-----------------------------");
			System.out.println("S Select a movie array to navigate");
			System.out.println("n  Navigate " + genres[navigate] + " movies" + "(record " + count + ")");
			System.out.println("x  Exit");
			System.out.println("-----------------------------");
			System.out.print("Enter Your Choice: ");
			String c1 = kb.next().toUpperCase();
			switch (c1) {
			case "S": {
				System.out.println("-----------------------------");
				System.out.println("       Genre Sub-Menu    ");
				System.out.println("-----------------------------");
				System.out.println(" 1  musical           " + "(" + all_movies[0].length + "movies)");
				System.out.println(" 2  comedy          " + "(" + all_movies[1].length + "movies)");
				System.out.println(" 3  animation          " + "(" + all_movies[2].length + "movies)");
				System.out.println(" 4  adventure          " + "(" + all_movies[3].length + "movies)");
				System.out.println(" 5  drama          " + "(" + all_movies[4].length + "movies)");
				System.out.println(" 6  crime          " + "(" + all_movies[5].length + "movies)");
				System.out.println(" 7  biography          " + "(" + all_movies[6].length + "movies)");
				System.out.println(" 8  horror           " + "(" + all_movies[7].length + "movies)");
				System.out.println(" 9  action          " + "(" + all_movies[8].length + "movies)");
				System.out.println(" 10  documentary           " + "(" + all_movies[9].length + "movies)");
				System.out.println(" 11  fantasy          " + "(" + all_movies[10].length + "movies)");
				System.out.println(" 12  mystery          " + "(" + all_movies[11].length + "movies)");
				System.out.println(" 13  sci-fi          " + "(" + all_movies[12].length + "movies)");
				System.out.println(" 14  family         " + "(" + all_movies[13].length + "movies)");
				System.out.println(" 15  western         " + "(" + all_movies[14].length + "movies)");
				System.out.println(" 16  romance          " + "(" + all_movies[15].length + "movies)");
				System.out.println(" 17  thriller          " + "(" + all_movies[16].length + "movies)");
				System.out.println(" 18  Exit ");
				System.out.println("-----------------------------");

				System.out.println("Enter Your Choice:");
				int choice = kb.nextInt();
				if (choice == 18) {
					break;
				}
				navigate = choice - 1;

				break;
			}
			case "N": {
				System.out.println("Navigating " + genres[navigate] + " movies" + "(" + count + ")");
				System.out.print("Enter Your Choice:");
				int choice = kb.nextInt();
				if (choice < 0) {
					initial2 = initial;
					initial = initial + (choice - 1);
					if (initial2 < -initial) {
						System.out.println("EOF has been reached");
						for (int i = initial2; i > 0; i--) {
							System.out.println(all_movies[navigate][i]);
						}
						initial = 0;
					} else {
						for (int i = initial2; i > initial; i--) {
							System.out.println(all_movies[navigate][i]);
						}
					}

				} else if (choice > 0) {
					initial2 = initial;
					initial = initial + (choice - 1);
					if (all_movies[navigate].length < initial) {
						System.out.println("EOF has been reached");
						for (int i = initial2; i < all_movies[navigate].length; i++) {
							System.out.println(all_movies[navigate][i]);
						}
						initial = all_movies[navigate].length - 1;
					} else {
						for (int i = 0; i <= initial; i++) {
							System.out.println(all_movies[navigate][i]);
						}
					}
				} else {
					break;
				}
				break;
			}
			case "X": {
				System.exit(0);

			}

			}
		}
	}

	 /**
     * Loads movies from a CSV file into an array of Movie objects.
     *
     * @param csvFileName The name of the CSV file containing movie records.
     * @return An array of Movie objects parsed from the CSV file.
     */
	public static Movie[] loadMovies(String csvFileName) {
		Scanner fileScanner = null;
		String line = null;
		Movie[] movies = null;
		int count = 0;
		int i = 0;

		try {
			fileScanner = new Scanner(new FileInputStream(csvFileName));
			count = Count(csvFileName);
			fileScanner.close();
			// Creating an array to store Movie objects
			movies = new Movie[count];
			fileScanner = new Scanner(new FileInputStream(csvFileName));
			while (fileScanner.hasNextLine()) {
				line = fileScanner.nextLine();
				String[] partByQoute = line.split("\"");
				if (partByQoute.length == 1) {
					String[] partByComma = line.split(",");
					movies[i++] = new Movie(Integer.parseInt(partByComma[0]), partByComma[1],
							Integer.parseInt(partByComma[2]), partByComma[3], partByComma[4],
							Double.parseDouble(partByComma[5]), partByComma[6], partByComma[7], partByComma[8],
							partByComma[9]);
				} else if (partByQoute.length == 3) {
					String[] partByComma = partByQoute[2].substring(1).split(",");
					movies[i++] = new Movie(Integer.parseInt(partByQoute[0].substring(0, partByQoute[0].length() - 1)),
							partByQoute[1].trim(), Integer.parseInt(partByComma[0]), partByComma[1], partByComma[2],
							Double.parseDouble(partByComma[3]), partByComma[4], partByComma[5], partByComma[6],
							partByComma[7]);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("CSV file not found: " + csvFileName);
		} catch (NumberFormatException e) {
			System.out.println("Error: Invalid numeric format in CSV file");
		} finally {
			if (fileScanner != null) {
				fileScanner.close();
			}
		}

		return movies;
	}

	/**
     * Counts the number of records in a file.
     *
     * @param fileName The name of the file to count records.
     * @return The number of records in the file.
     */
	public static int Count(String FileName) {
		int count = 0;
		try {
			Scanner s1 = new Scanner(new FileInputStream(FileName));
			while (s1.hasNextLine()) {
				s1.nextLine();
				count++;
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return count;
	}

	/**
     * Retrieves the index of a genre in the genres array.
     *
     * @param genre The genre to find the index of.
     * @return The index of the genre in the genres array, or -1 if not found.
     */
	public static int getGenreIndex(String genre) {
		String[] validGenres = { "musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror",
				"action", "documentary", "fantasy", "mystery", "sci-fi", "family", "romance", "thriller", "western" };
		for (int i = 0; i < validGenres.length; i++) {
			if (genre.equalsIgnoreCase(validGenres[i])) {
				return i;
			}
		}
		return -1; // Invalid genre
	}

	/**
     * Generates the file name for a given genre.
     *
     * @param index The index of the genre in the genres array.
     * @return The file name corresponding to the genre.
     */
	public static String getGenreFileName(int index) {
		String[] genres = { "musical", "comedy", "animation", "drama", "crime", "biography", "horror", "action",
				"documentary", "fantasy", "mystery", "sci-fi", "family", "romance", "thriller", "western" };
		return genres[index] + ".csv";
	}

	/**
     * Displays content (reads) from a scanner object.
     *  
     *
     * @param scanner The scanner containing content to display.
     */
	public static void display(Scanner scanner) {
		String line;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			System.out.println(line);
		}
	}

	/**
     * Validates a year value.
     *
     * @param year The year to validate.
     * @throws SemanticErrors.InvalidPart If the year is not within the valid range.
     */
	public static void validYear(int year) throws SemanticErrors.InvalidPart {
		if (year > 1999 || year < 1990)
			throw new SemanticErrors.InvalidPart("year in not valid !");
	}

	/**
     * Validates a duration value.
     *
     * @param duration The duration to validate.
     * @throws SemanticErrors.InvalidPart If the duration is not within the valid range.
     */
	public static void validDuration(int duration) throws SemanticErrors.InvalidPart {
		if (duration > 300 || duration < 30)
			throw new SemanticErrors.InvalidPart("the duration in not valid !");
	}
	 /**
     * Validates a score value.
     *
     * @param score The score to validate.
     * @throws SemanticErrors.InvalidPart If the score is not within the valid range.
     */
	public static void validScore(double score) throws SemanticErrors.InvalidPart {
		if (score < 0 || score > 10)
			throw new SemanticErrors.InvalidPart("the score is invalid !");
	}
	
	 /**
     * Validates a rating value.
     *
     * @param rating The rating to validate.
     * @throws SemanticErrors.InvalidPart If the rating is not a valid value.
     */
	public static void validRating(String rating) throws SemanticErrors.InvalidPart {
		if (!(rating.equals("PG") || rating.equals("Unrated") || rating.equals("G") || rating.equals("R")
				|| rating.equals("PG-13") || rating.equals("NC-17")))
			throw new SemanticErrors.InvalidPart("the rate is invalid !");
	}

	/**
     * Validates a genre value.
     *
     * @param genre The genre to validate.
     * @throws SemanticErrors.InvalidPart If the genre is not a valid value.
     */
	public static void validGenre(String genre) throws SemanticErrors.InvalidPart {
		if (!(genre.equalsIgnoreCase("musical") || genre.equalsIgnoreCase("comedy")
				|| genre.equalsIgnoreCase("animation") || genre.equalsIgnoreCase("adventure")
				|| genre.equalsIgnoreCase("drama") || genre.equalsIgnoreCase("crime")
				|| genre.equalsIgnoreCase("biography") || genre.equalsIgnoreCase("horror")
				|| genre.equalsIgnoreCase("action") || genre.equalsIgnoreCase("documentary")
				|| genre.equalsIgnoreCase("fantasy") || genre.equalsIgnoreCase("mystery")
				|| genre.equalsIgnoreCase("sci-fi") || genre.equalsIgnoreCase("family")
				|| genre.equalsIgnoreCase("romance") || genre.equalsIgnoreCase("thriller")
				|| genre.equalsIgnoreCase("western")))
			throw new SemanticErrors.InvalidPart("The genre in invalid! ");
	}

	/**
     * The main method of the program.
     * Executes part 1, part 2, part 3 processing, and displays the main menu.
     */
	public static void main(String[] args) {

		Movie movie = new Movie();
		
		String FileName = "part1 manifest.txt";
		do_part1(FileName);

		do_part2(FileName);
		do_part3();
		Menu();

	}

}

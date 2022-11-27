// Programmer: Sukhnain Deol
// Date: 11/15/22
// Class: Computer Science 141
// Assignment: Calendar Part 3

// Program Function:
    // This program will prompt the user with a menu of options to choose from.
        // e to enter a date's month calendar to be printed
        // t to print today's date and the current month in calendar form
        // p to go to previous month calendar
        // n to go to next month calendar
        // ev to add an event to the calendar
        // fp to print the calendar to a file
        // q to quit the program


// Extra credit: 
    // none

// Bugs/Problems:
    //

// To-Do
    // comments
    // maybe make events multi-line??
    // test adding event to end of each month (calendar size calc for max characters)


// Done
    // make date indicator not interfere with events
    // make calendar pull  event file before printing
    // create events array input
    // create jagged array for full year
    // create event input
        // format of creating/importing events (“MM/DD event_title”)
    // implement print event into date boxes
    // allowz user to enter a date and event to add to the calendar
    // allow user to export calendar into inputted file
        // make sure file is closed once finish printed
        // make it so input only inputs month, no day indicator


  import java.util.Calendar; // used for functions that need to access specific calendar information
  import java.time.LocalDate; // used to access current dates for calendar
  import java.util.Scanner; // allows access to user input
  import java.io.File;
  import java.io.FileNotFoundException;
  import java.io.PrintStream;
  
  public class SDeolCalendar3
  {
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner in = new Scanner(System.in); // creates scanner object to access inputs
        Scanner eventIn = new Scanner(new File ("calendarEvents.txt")); // accesses file to pull events from
        PrintStream calendarOut = new PrintStream(new File ("output.txt")); // used to print calendaer out if user wishes (placeholder file)

        LocalDate date = LocalDate.now();// java.date.LocalDate library creates calendar object

        String dateInput = "00/00"; // input of date storage (placeholder value)
        String eventInputted = "My_Birthday"; // event storage (placeholder value)

        int inputDay = 0; // holds input of user for day
        int inputMonth = 0; // holds input of user for month
        int monthLength = 0; // hold the length of month (e.g. january is 31 days)
        final int calendarSize = 11; 
        int carLocation = ((7*(calendarSize +2))-40)/2; // used to determine where the car will be printed
        int currentMonth = date.getMonthValue();
        int currentDay = date.getDayOfMonth();

        boolean calendarIsOpen = false; // check if calendar is open before being able to quit or go next/previous
        boolean calendarInUse = true; // turns off calendar loop when 'q' is inputted in menu
        boolean isLeapYear = false;  // lets program know if to calculate based on a leap year

        // checks if its a leap year
        if (date.getYear()% 4 == 0) // if leap year
        {isLeapYear = true;}

        String[][] eventArr = new String[12][]; // jagged array that can hold events for each day of the year
        {
            eventArr[0] = new String[31]; // January
            eventArr[1] = new String[28]; // February
            eventArr[2] = new String[31]; // March
            eventArr[3] = new String[30]; // April
            eventArr[4] = new String[31]; // May
            eventArr[5] = new String[30]; // June
            eventArr[6] = new String[31]; // July
            eventArr[7] = new String[31]; // August
            eventArr[8] = new String[30]; // September
            eventArr[9] = new String[31]; // October
            eventArr[10] = new String[30]; // November
            eventArr[11] = new String[31]; // December
        } 

        while(eventIn.hasNextLine()) // pulls events from event file
        {
            String event = eventIn.nextLine(); // takes in line from event file
            String[] eventLineArr = event.split(" "); // splits date and event into array
            int eventDay = dayFromDate(eventLineArr[0]); // split date into day
            int eventMonth = monthFromDate(eventLineArr[0]); // split date into month
            String eventTitle = eventLineArr[1]; // assigns event title to string
            eventArr[eventMonth-1][eventDay-1] = eventTitle; // adds event to date in array 
        }

        // start of menu loop
        do {
            System.out.println("Please Enter One Of The Following Commands");
            System.out.println("'e' to display an inputted date");
            System.out.println("'t' to display current date");
            System.out.println("'n' to display next month");
            System.out.println("'p' to display previous month");
            System.out.println("'ev' to add an event to a date");
            System.out.println("'fp' to print a calendar month to a file");
            System.out.println("'q' to quit the program");
        
            String menuInput = in.next().toLowerCase(); // takes in next token inputted in lower case
            in.nextLine(); // clears inputs

            switch(menuInput) 
            {
                case "e": // user enters a date to print
                    dateInput = askForDateinput(isLeapYear, in); // asks user for input date
                    inputMonth = monthFromDate(dateInput); // splits date into month
                    inputDay = dayFromDate(dateInput); // splits date into day
                    monthLength = findMonthLength(inputMonth); // determines length of month
                    // Print Inputted Month Calendar 
                    printCalendar(carLocation, calendarSize, date, monthLength, inputMonth, inputDay, eventArr);                    
                    calendarIsOpen = true; // states calendar is opened 

                    break;
                case "t": // prints today's date
                    inputMonth = currentMonth; // splits date into month
                    inputDay = currentDay; // splits date into day
                    monthLength = findMonthLength(inputMonth); // determines length of month
                    // Print Inputted Month Calendar 
                    printCalendar(carLocation, calendarSize, date, monthLength, inputMonth, inputDay, eventArr);       
                    calendarIsOpen = true; // states calendar is opened 

                    break;
                case "n": // print next month
                    if (calendarIsOpen) // if calendar is open
                    {
                        if(inputMonth < 12) // if calendar is less than 12 add 1
                            {inputMonth++;}
                        else
                            {inputMonth = 1;} // calendar is 12 or higher, go to month 1
                        monthLength = findMonthLength(inputMonth);// changes calendar length to fit month
                        printCalendar(carLocation, calendarSize, date, monthLength, inputMonth, inputDay, eventArr);                    
                    }
                    else  // if calendar isnt open
                        {System.out.println("\nERROR: Please open the calendar before changing it.\n");}

                    break;
                case "p": // print previous month
                    if (calendarIsOpen) // if calendar is open
                    {
                        if(inputMonth == 1) // if month is 1, goes back to 12
                            {inputMonth = 12;}
                        else // else go back one month
                            {inputMonth--;}
                        monthLength = findMonthLength(inputMonth);// changes calendar length to fit month
                        printCalendar(carLocation, calendarSize, date, monthLength, inputMonth, inputDay, eventArr);                    
                    }
                    else // if calendar isnt open
                        {System.out.println("\nERROR: Please open the calendar before changing it.\n");}

                    break;
                case "ev": // add event to calendar
                    System.out.println("Please enter  date and event (mm/dd event_title) (Under "+ (calendarSize+2)+ " characters):");
                    eventInputted = in.nextLine(); 
                    if (eventInputted.length() > 18) // 42 accounts for mm/dd and spaces
                        {System.out.println("\nERROR: Event title is too long. Please try again.\n");}
                    else if (eventInputted.replace(" ", "").length() < 6) // if no event is added
                        {System.out.println("\nERROR: No event was added. Please try again.\n");}
                    else
                    {
                        inputDay = dayFromDate(eventInputted); // gets day from input
                        inputMonth = monthFromDate(eventInputted); // gets month from input
                        eventArr[inputMonth-1][inputDay-1] = eventInputted.substring(6); // adds event to array
                        System.out.println("\nSuccess! '" + eventArr[inputMonth-1][inputDay-1] + "' event added to " + eventInputted.substring(0,5) + "\n");
                    }
                        
                    break;
                case "fp": // prints calendar to inputted file
                    inputMonth = askForMonthInput(isLeapYear, in); // takes in month to print
                    System.out.println("Please enter a file name to print to:"); // asks for file 
                    calendarOut = new PrintStream(in.next()); // takes in next token as file
                    monthLength = findMonthLength(inputMonth); // determines length of month

                    printCalendarToFile(carLocation, calendarSize, date, monthLength, inputMonth, eventArr, calendarOut);

                    System.out.println("\nSuccess! Calendar printed to file.\n");

                    calendarOut.close(); // closes file

                    break;
                case "q": // quit
                    if (calendarIsOpen) // if calendar is open
                        {calendarInUse = false;} // marks calendar to be closed
                    else  // if calendar isnt  open
                        {System.out.println("\nERROR:Please open the calendar before quitting.\n");}

                    break;
                default:
                    System.out.println("\nERROR: Invalid Input\n");
                    break;
            }

        } while (calendarInUse); // end of menu do-while

        in.close(); // closes scanner to prevent resource leaks
        eventIn.close(); // closes scanner to prevent resource leaks
        calendarOut.close(); // closes printstream to prevent resource leaks
    }// end of main method




    // prompts user to input a date, checks to make sure it is a real date 
    public static String askForDateinput(boolean isLeapYear, Scanner in)
    {
        boolean realDateInputted = false; // used to check if user gives a real date on a calendar
        String dateInput =""; // holds date input from user;
        // repeatedly asks for input until valid one is given
        while(realDateInputted == false)
        {
            // asks for date input and stores (mm/dd)
            System.out.println("Please Enter a Date (mm/dd): ");
            dateInput = in.nextLine(); // takes in mm/dd input

            // checks if input is formatted correclty and that the date is real
            if (dateInput.length() == 5 && dateInput.charAt(2) == '/' && isRealDate(dateInput, isLeapYear, in)) 
                {realDateInputted = true;} // ends loop
            else
                {System.out.println("\nERROR: Invalid Input\n");}
        }
        return dateInput;
    } // end of askForDateinput method



    // prompts user to input a date, checks to make sure it is a real date 
    public static int askForMonthInput(boolean isLeapYear, Scanner in)
    {
        boolean realDateInputted = false; // used to check if user gives a real date on a calendar
        String monthInput =""; // holds date input from user;
        int monthInt = 1; // holds month as an int (placeholder value) 
        // repeatedly asks for input until valid one is given
        while(realDateInputted == false)
        {
            System.out.println("Please Enter a Month (mm): ");
            monthInput = in.nextLine(); // takes in mm/dd input
            try // checks if input is an int
            {
                monthInt = Integer.parseInt(monthInput); // converts input to int
                if (monthInt > 0 && monthInt < 13) // checks if input is between 1 and 12
                    {realDateInputted = true;} // ends loop
                else
                    {System.out.println("\nERROR: Invalid Input\n");}
            }
            catch (NumberFormatException e) // if input is not an int
                {System.out.println("\nERROR: Invalid Input\n");}
        }
        return monthInt;
    } // end of askForDateinput method



    // checks if date is real
    public static boolean isRealDate(String dateInput, boolean isLeapYear, Scanner in)
    {

        // tries following code, prints error if doesnt work
        try 
        {
            // checks if input is a real date on the calendar
            switch (monthFromDate(dateInput))
            { 
                // months with 31 days
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                // if day is between or equal to 1-31
                if (dayFromDate(dateInput) <=31 && dayFromDate(dateInput) >= 1)
                    {return true;}
                else 
                    {return false;}

                // months with 30 days
                case 4:
                case 6:
                case 9:
                case 11:
                // if day is between or equal to 1-30
                if (dayFromDate(dateInput) <= 30 && dayFromDate(dateInput) >= 1)
                    {return true;}
                else 
                    {return false;}

                // month with 29 days
                case 2:
                // if day is between or equal to 1-29 and leap year
                if (isLeapYear && dayFromDate(dateInput) <= 29 && dayFromDate(dateInput) >= 1 )
                    {return true;} 
                else if (isLeapYear == false && dayFromDate(dateInput) <= 28 && dayFromDate(dateInput) >= 1 )
                    {return true;}
                else 
                    {return false;}

                default:
                return false;


            } // end of month from date switch/case
        } // end of try
        catch(Exception e) // sends error if incorrect input
            {return false;}
        
    } // end of isRealDate



    // uses inputted month to find length of month
    public static int findMonthLength(int month)
    {
        Calendar calendar = Calendar.getInstance(); // creates calendar object
        calendar.set(Calendar.MONTH, month -1); // months in this function are from 0-11

        int monthLength = calendar.getActualMaximum(Calendar.DATE); // gets max number of days in month field of calendar

        return monthLength;
    } // end of findMonthLength



    // prints a given number of a given character
    public static void addChar(String addChar, int amount)
    {
        for (int i = 0; i < amount; i++)
            {System.out.print(addChar);}
    }// end of addChar



    // prints a given number of a given character
    public static void addChar(String addChar, int amount, PrintStream calendarOut)
    {
        for (int i = 0; i < amount; i++)
            {calendarOut.print(addChar);}
    }// end of addChar



    // Function: draws a 7x5 calendar with the month numnber on top
    public static void drawMonth(int month, int day, int size, LocalDate date, int monthLength, String[][] eventArr) 
    {
        int calendarLength = 7 *(size+2);
        int calendarHalfLength = calendarLength/2 - ((calendarLength %2)/2); // rounded down

        // prints current month in the middle of calendar's top 
        addChar(" ", calendarHalfLength);
        System.out.println(month); // prints month in center top
        
        // prints top border of equal signs 
        addChar("=", calendarLength);

        System.out.println(); // moves to next line
        
        int currentYear = date.getYear(); 
        int monthStartDay = monthStartDay(month, currentYear);

        int maxRows = 7; // max rows possible in calendar - 1
        int totalCalendarSlots = monthLength + monthStartDay -1; // total spaces taken up by calendar

        if (totalCalendarSlots > 35) 
            {maxRows = 7;}// if calendar needs 6 rows
        else if (totalCalendarSlots < 36 & totalCalendarSlots > 28 ) 
            {maxRows = 6;}// if calendar needs 5 rows
        else 
            {maxRows = 5;}// if calendar needs 4 rows

        // prints 7x5 calendar
        for (int i = 1; i < maxRows; i++)
            {drawRow(i, month, size, date, monthStartDay, monthLength, day, eventArr);} // draws 5 rows

    } // end of drawMonth



    // Function: prints a week of a month
    public static void drawRow(int row, int month, int size, LocalDate date, int monthStartDay, int monthLength, int day, String[][] eventArr)
    {
        int rowLength = 7 *(size+2);
        int boxLength = size + 1;
        int boxHeight = (((size + 2)/3) - (((size + 2)%3)/3)); // rounded down int value of of size +2

        int rowsPrinted = 1; // keeps track of how many rows *in a calendar row*
        
        int boxDayNum = 0-monthStartDay+1 + 7*(row-1);
        boolean indicatorPrinted = false; // used to check if indicator has been printed

        int printedDay;

        // prints top layer of row
        for(int k = (row * 7 - 6); k <= 7*row; k++)
        {
            printedDay = k - monthStartDay + 1; // actual day printed on console

            System.out.print("|"); // end of square

            if (printedDay <= monthLength && k >= monthStartDay)
                {System.out.print(printedDay);} // print date
            else if (k < monthStartDay)
                {System.out.print(" ");}
            else if (k > monthLength) 
                {System.out.print("  ");} // print blank space (if date not on calendar)

            if (printedDay < 10) // takes account for extra digit space
                {addChar(" ", boxLength-1);} // single digit
            else
                {addChar(" ", boxLength-2);}  // double digit (removes one space)

        }

        System.out.println("|"); // prints end of calendar and goes to next line
        // prints empty rows
        for (int i = 0; i < boxHeight; i++)
        {
            for (int l = 0; l < 7; l++)
            {
                System.out.print("|");
                boxDayNum++;
                try { // catches any array index issues
                    // adds day indicator
                    if (boxDayNum == day & indicatorPrinted == false & rowsPrinted == 1) 
                    {
                        addChar(" ",(boxLength/2 -1));
                        System.out.print("*");
                        addChar(" ", boxLength/2 + ((size-1) % 2));
                        indicatorPrinted = true; // makes sure indicator is only printed once
                    }
                    // adds event if on row after indicator, if there is space, and an event exists for the date
                    else if (rowsPrinted == 2 & rowsPrinted < boxHeight & eventArr[month-1][boxDayNum-1] != null) 
                    {
                        String printedEvent = eventArr[month-1][boxDayNum-1]; // stores event
                        double eventSpacing = (boxLength - printedEvent.length())/2; // calcs spacing between event and box
                        addChar(" ", (int)Math.floor(eventSpacing));
                        System.out.print(printedEvent.replace("_", " "));
                        if (printedEvent.length() % 2 != 0) // prevents mis alignment
                            {addChar(" ", (int)Math.ceil(eventSpacing+1));}
                        else
                            {addChar(" ", (int)Math.ceil(eventSpacing));}
                    }
                    else // if no event or indicator to be added
                        {addChar(" ",boxLength);}
                    
                }
                catch (ArrayIndexOutOfBoundsException e) // defaults to adding spaces if error
                    {addChar(" ",boxLength);}

                if (l == 6) // ends each row with hyphen
                    {System.out.print(("|"));}
            }
            System.out.println();
            boxDayNum -= 7;

            rowsPrinted++;
        }

        // prints bottom border of equal signs 
        addChar("=", rowLength);

        System.out.println();
    } // end of drawRow



    // takes in year and month and outputs the starting day (e.g. 1 for Sunday, 7 for Saturday) of that month in that year
    public static int monthStartDay(int month, int year)
    {
        Calendar date = Calendar.getInstance(); // creates calendar object

        date.set(date.MONTH, month-1); // sets month to inputted, -1 because its 0-11
        date.set(date.DAY_OF_MONTH, 1); // sets day of month to the first one

        return date.get(date.DAY_OF_WEEK); // finds the day the first of the month is
    } // end of monthStartDay



    // prints inputted date
    public static void displayDate(int month, int day) 
    {
        // prints day and month each on separate lines 
        System.out.println("\nMonth: " + month); // inputted month
        System.out.println("Day: " + day); // inputted day
    }// end of displayDate



    // get month int from a date (mm/dd)
    public static int monthFromDate(String date)
    {
        // stores month portion of date as integer
        int inputMonth = Integer.parseInt(date.substring(0, 2)); 
        
        return inputMonth;
    } // end of monthFromDate



    // get day int from date (mm/dd)
    public static int dayFromDate(String date)
    {
        // stores day portion of date as integer
        int inputDay = Integer.parseInt(date.substring(3, 5)); 
        
        return inputDay;
    } // end of dayFromDate




    public static void printCalendar(int carLocation, int calendarSize, LocalDate date, int monthLength, int month, int day, String[][] eventArr)
    {
        PrintStream originalStream = System.out; // stores original output stream
        // Print Inputted Month Calendar 
        printCar(carLocation, originalStream); // ascii art for calendar
        System.out.println();
        drawMonth(month, day, calendarSize, date, monthLength, eventArr); // prints inputted calendar month
        displayDate(month, day); // prints inputted day and month seperately
        System.out.println(); 
    } // end of printCalendar



    // Function: prints a calendar for a given month to a file
    public static void printCalendarToFile(int carLocation, int calendarSize, LocalDate date, int monthLength, int month, String[][] eventArr, PrintStream calendarOut)
    {
        // Print Inputted Month Calendar 
        printCar(carLocation, calendarOut); // ascii art for calendar
        calendarOut.println();
        printMonthToFile(month, calendarSize, date, monthLength, eventArr, calendarOut); // prints inputted calendar month
        calendarOut.println();
    } // end of printCalendarToFile



    // Function: prints a 7x5 calendar with the month numnber on top to an inputted file
    public static void printMonthToFile(int month, int size, LocalDate date, int monthLength, String[][] eventArr, PrintStream calendarOut) 
    {
        int calendarLength = 7 *(size+2);
        int calendarHalfLength = calendarLength/2 - ((calendarLength %2)/2); // rounded down

        // prints current month in the middle of calendar's top 
        addChar(" ", calendarHalfLength, calendarOut);
        calendarOut.println(month); // prints month in center top
        
        // prints top border of equal signs 
        addChar("=", calendarLength, calendarOut);

        calendarOut.println(); // moves to next line
        
        int currentYear = date.getYear(); 
        int monthStartDay = monthStartDay(month, currentYear);

        int maxRows = 7; // max rows possible in calendar - 1
        int totalCalendarSlots = monthLength + monthStartDay -1; // total spaces taken up by calendar

        if (totalCalendarSlots > 35) 
            {maxRows = 7;}// if calendar needs 6 rows
        else if (totalCalendarSlots < 36 & totalCalendarSlots > 28 ) 
            {maxRows = 6;}// if calendar needs 5 rows
        else 
            {maxRows = 5;}// if calendar needs 4 rows

        // prints 7x5 calendar
        for (int i = 1; i < maxRows; i++)
            {printRowToFile(i, month, size, date, monthStartDay, monthLength, eventArr, calendarOut);} // draws 5 rows

    } // end of drawMonth



    // Function: prints a week of a month to an inputted file
    public static void printRowToFile(int row, int month, int size, LocalDate date, int monthStartDay, int monthLength, String[][] eventArr, PrintStream calendarOut)
    {
        int rowLength = 7 *(size+2);
        int boxLength = size + 1;
        int boxHeight = (((size + 2)/3) - (((size + 2)%3)/3)); // rounded down int value of of size +2

        int rowsPrinted = 1; // keeps track of how many rows *in a calendar row*
        
        int boxDayNum = 0-monthStartDay+1 + 7*(row-1);

        int printedDay; // keeps track of what day is to be printed

        // prints top layer of row
        for(int k = (row * 7 - 6); k <= 7*row; k++)
        {
            printedDay = k - monthStartDay + 1; // actual day printed on console

            calendarOut.print("|"); // end of square

            if (printedDay <= monthLength && k >= monthStartDay)
                {calendarOut.print(printedDay);} // print date
            else if (k < monthStartDay)
                {calendarOut.print(" ");}
            else if (k > monthLength) 
                {calendarOut.print("  ");} // print blank space (if date not on calendar)

            if (printedDay < 10) // takes account for extra digit space
                {addChar(" ", boxLength-1, calendarOut);} // single digit
            else
                {addChar(" ", boxLength-2, calendarOut);}  // double digit (removes one space)
        }

        calendarOut.println("|"); // prints end of calendar and goes to next line
        // prints empty rows
        for (int i = 0; i < boxHeight; i++)
        {
            for (int l = 0; l < 7; l++)
            {
                calendarOut.print("|");
                boxDayNum++;
                try { // catches any array index issues
                    // prints events
                    if (rowsPrinted == 2 & rowsPrinted < boxHeight & eventArr[month-1][boxDayNum-1] != null) 
                    {
                        String printedEvent = eventArr[month-1][boxDayNum-1]; // assign event to var
                        double eventSpacing = (boxLength - printedEvent.length())/2; // calculates spacing
                        addChar(" ", (int)Math.floor(eventSpacing), calendarOut); // prints spacing
                        calendarOut.print(printedEvent.replace("_", " ")); // removes '_' from event name
                        if (printedEvent.length() % 2 != 0) // prevents mis alignment
                            {addChar(" ", (int)Math.ceil(eventSpacing+1), calendarOut);} // prints end spacing
                        else
                            {addChar(" ", (int)Math.ceil(eventSpacing), calendarOut);} // prints end spacing
                    }
                    else // if no event or indicator to be added
                        {addChar(" ",boxLength, calendarOut);}
                }
                catch (ArrayIndexOutOfBoundsException e) // defaults to adding spaces if error
                    {addChar(" ",boxLength, calendarOut);}

                if (l == 6) // ends each row with hyphen
                    {calendarOut.print(("|"));}
            }
            calendarOut.println();
            boxDayNum -= 7;

            rowsPrinted++;
        }
        // prints bottom border of equal signs 
        addChar("=", rowLength, calendarOut);

        calendarOut.println();
    } // end of drawRow



    //prints a car with a changeable location
    public static void printCar(int distanceFromEdge, PrintStream calendarOut)
    { 
        // moves car location
        distanceFromEdge += 8; // must be postive
        // top of car 
        addChar(" ", distanceFromEdge, calendarOut);
        addChar("_", 24, calendarOut);

        // 2nd layer, starting edges and windows
        calendarOut.println();
        addChar(" ", distanceFromEdge + 1 , calendarOut);
        calendarOut.print("/"); // edge
        // top of window
        addChar(" ", 4, calendarOut);
        addChar("_", 4, calendarOut);
        addChar(" ", 5, calendarOut);
        addChar("_", 4, calendarOut);
        addChar(" ", 3, calendarOut);
        calendarOut.println("\\"); // edge & ends line

        // 3rd layer, continuing edge and windows
        addChar(" ", distanceFromEdge, calendarOut);
        calendarOut.print("/");
        addChar(" ", 4, calendarOut);
        calendarOut.print("/");
        addChar(" ", 4, calendarOut);
        calendarOut.print("|");
        addChar(" ", 4, calendarOut);
        calendarOut.print("|");
        addChar(" ", 3, calendarOut);
        calendarOut.print("|");
        addChar(" ", 3, calendarOut);
        calendarOut.println("\\"); // edge & ends line

        // 4th layer stars body of car
        addChar(" ", distanceFromEdge - 7, calendarOut);

        addChar("_", 6, calendarOut);
        calendarOut.print("/");


        addChar(" ", 4, calendarOut);
        calendarOut.print("/");

        addChar("_", 5, calendarOut);
        calendarOut.print("|");

        addChar(" ", 4, calendarOut);
        calendarOut.print("|");
        addChar("_", 3, calendarOut);
        calendarOut.print("|");
        addChar(" ", 4, calendarOut);
        calendarOut.print("\\");
        addChar("_", 6, calendarOut);
        calendarOut.println();

        //5th layer doors handles and light
        addChar(" ", distanceFromEdge - 7, calendarOut);
        calendarOut.print("|");
        addChar(" ", 14, calendarOut);
        calendarOut.print("-");
        addChar(" ", 7, calendarOut);
        calendarOut.print("-");
        addChar(" ", 11, calendarOut);
        addChar("_", 3, calendarOut);
        calendarOut.print("\\");

        calendarOut.println();

        // 6th layer start of tires and end of lights
        addChar(" ", distanceFromEdge - 7, calendarOut);
        calendarOut.print("|");
        addChar(" ", 8, calendarOut);
        addChar("_", 4, calendarOut);
        addChar(" ", 11, calendarOut);
        addChar("_", 4, calendarOut);
        addChar(" ", 7, calendarOut);

        calendarOut.print("\\");
        calendarOut.print("_");
        calendarOut.print("/");
        calendarOut.print("|");
        calendarOut.println();

        //7th layer end of body, continue tires
        addChar(" ", distanceFromEdge - 7, calendarOut);
        calendarOut.print("|");
        addChar("_", 7, calendarOut);
        calendarOut.print("/");
        addChar(" ", 4, calendarOut);
        calendarOut.print("\\");
        addChar("_", 9, calendarOut);
        calendarOut.print("/");
        addChar(" ", 4, calendarOut);
        calendarOut.print("\\");
        addChar("_", 9, calendarOut);
        calendarOut.print("/");
        calendarOut.println();

        // 8th layer 
        addChar(" ", distanceFromEdge + 1, calendarOut);
        calendarOut.print("\\");
        addChar("_", 4, calendarOut);
        calendarOut.print("/");
        addChar(" ", 9, calendarOut);

        calendarOut.print("\\");
        addChar("_", 4, calendarOut);
        calendarOut.print("/");
        addChar(" ", 9, calendarOut);
        calendarOut.println();
    }// end of printCar
}// end of class
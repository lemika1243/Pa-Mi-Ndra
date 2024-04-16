package mpanampy;

import java.io.*;
import java.lang.reflect.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class Auto {
    Object object;
    String[] objectForm;

    public Auto() {
    }

    public Auto(Object v) throws Exception {
        object = v;
        objectForm = new String[toFunction(object).length];
        for (int i = 0; i < objectForm.length; i++) {
            objectForm[i] = "";
        }
    }

    // SET AND GET FUNCTIONS
    public Object getObject() {
        return object;
    }

    public void setObject(Object v) {
        object = v;
    }

    public String getObjectForm(int ind) {
        return objectForm[ind % objectForm.length];
    }

    public String[] getObjectForm() {
        return objectForm;
    }

    public void setObjectForm(int ind, String a) {
        objectForm[ind % objectForm.length] = a;
    }
    // END




    

    /// SUM FUNCTION
        /**
         * Make the sum of a method in a list of an object
         * 
         * @param lo  the list of object
         * @param met the method name to call
         * @return the sum of the return of the method met
         */
        public static double sum(List lo, String met) throws Exception {
            double valiny = 0;
            Method m = lo.get(0).getClass().getMethod(met);
            for (int i = 0; i < lo.size(); i++) {
                valiny += (double) m.invoke(lo.get(i));
            }
            return valiny;
        }

        /**
         * Make the sum of a method in a list of an object
         * 
         * @param lo  the list of object
         * @param met the methods names to call
         * @return the array of the sum of all the returns of all the methods met
         */
        public static double[] sum(List lo, String[] met) throws Exception {
            double[] valiny = new double[met.length];
            for (int i = 0; i < met.length; i++) {
                valiny[i] = sum(lo, met[i]);
            }
            return valiny;
        }
    /// END






    
    /// OPERATIONS FOR DATES

        /**
         * transform a Date into String
         * @param d a date in java.util to transform into String
         * @param detailed to make the String return a datetime or not
         * @return a String of date
         */
        public static String convertToString(Date d, boolean detailed) {
            String valiny = new String();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (detailed) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            }
            valiny = dateFormat.format(d);
            return valiny;
        }

        /**
         * transform a String into Date util in java
         * @param a the date string to convert
         * @param detailed to make the Date return a datetime or not
         * @return a Date in java.util
         */
        public static Date convertToDate(String a, boolean detailed) throws Exception {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            if (!detailed)
                s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return s.parse(a);
        }


    /// END








    /// CONCERNING FILES

        public static void createFolder(String path) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        public static void createFolders(String filename) {
            File file = new File(filename);
            String[] folder = filename.split("/");
            for (int i = 0; i < folder.length - 1; i++) {
                createFolder(folder[i]);
            }
        }

        public static void createFolders(String mom, String filename) {
            File file = new File(filename);
            String[] folder = filename.split("/");
            String flds = mom;
            for (int i = 0; i < folder.length - 1; i++) {
                flds += "/" + folder[i];
            }
            Auto.createFolder(flds);
        }


        /**
         * get all the containing of file in String
         * @param file the file
         * @return an arraylist of String contained in the file
         */
        public static ArrayList<String> getAllIn(String file) {
            ArrayList<String> valiny = new ArrayList<String>();
            String a = new String();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while ((a = reader.readLine()) != null) {
                    valiny.add(a);
                }
                reader.close();
            } catch (Exception e) {
            }
            return valiny;
        }

        /**
         * save the Strings in the file
         * @param fileName the path of the file
         * @param files the values of the file to add on
         */
        public void saveData(String fileName, String... files) {
            try {
                BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
                for (int i = 0; i < files.length; i++) {
                    w.write(files[i] + "\n");
                }
                w.close();
            } catch (Exception e) {
            }
        }

        /**
         * save the Strings in the file
         * @param fileName the path of the file
         * @param files the values of the file to add on
         */
        public void writeIn(String fileName, List<String> values) {
            try {
                BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
                for (String string : values) {
                    w.write(string + "\n");
                }
                w.close();
            } catch (Exception e) {
            }
        }
        
        /**
         * @param path the file path
         * @param nameIn the string to look for in the file
         * @return
         */
        public static List<String> grep(String path, String nameIn){
            List<String> valiny = new ArrayList<>();
            valiny = getIn(getAllIn(path), nameIn);
            return valiny;
        }

        // END

        
    /// END







    /// CONCERNING HTMLS

        /**
         * GENERATE THE BEGINING OF THE DOCTYPE in html
         * @param title 
         * @param style make a list of the path to import of css in this doctype
         * @return the String code html
         */
        public static String beginDoctype(String title, String... style) {
            String valiny = "";
            valiny = "<!DOCTYPE html><html><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'><title>"
                    + title + "</title>";
            for (String val : style) {
                valiny += "<link rel='stylesheet' href='" + val + "'>\n";
            }
            valiny += "</head><body>";
            return valiny;
        }
        // END

        /**
         * end the doctype in html
         * @return the String code html
         */
        public static String endDoctype() {
            String valiny = "</body></html>";
            return valiny;
        }
        //END

        /**
         * GET IN STRING THE TYPE OF AN INPUT FROM A GIVEN FIELD
         * @param c the field's object of this class
         * @return
         */
        String getType(Field c) {
            if (c.getType().getSimpleName() == "double" || c.getType().getSimpleName() == "Double"
                    || c.getType().getSimpleName() == "int" ||
                    c.getType().getSimpleName() == "Integer" || c.getType().getSimpleName().equals("float")) {
                return "number";
            } else if (c.getType().getSimpleName().equals("Date")) {
                return "date";
            } else if (c.getType().getSimpleName().equals("String") || c.getType().getSimpleName().equals("char")) {
                return "text";
            }
            return null;
        }
        // END

        /**
         * GET IN STRING THE TYPE OF ALL INPUT TO A GIVEN OBJECT
         * @return the html String code of all the types of the input of all the fields
         */
        String[] getTypeForm() {
            Object o = this.object;
            Field[] f = o.getClass().getDeclaredFields();
            Vector temp = new Vector();
            for (int i = 0; i < f.length; i++) {
                String name = getType(f[i]);
                if (name != null) {
                    temp.add(name);
                }
            }
            String[] valiny = new String[temp.size()];
            for (int x = 0; x < temp.size(); x++) {
                valiny[x] = (String) temp.elementAt(x);
            }
            return valiny;
        }
        // END

        /**
         * set all the input of this class's object fields into input in code html
         */
        void setHtmlForm() throws Exception {
            String[] func = toFunction(object);
            String[] type = getTypeForm();
            for (int i = 0; i < type.length; i++) {
                if (getObjectForm(i) == "") {
                    setObjectForm(i, "<p>" + func[i] + " : </p>" + "<input type='" + type[i] +
                            "' placeholder='your " + func[i] + "' name='" + func[i] + "'>\n");
                }
            }
        }
        // END

        /**
         * make a link of an html code
         * @param path where the link direct us
         * @param inside the output of the link
         * @return the String html code of the link
         */
        public static String makeLink(String path, String inside) {
            String query = "<a href='" + path + "'>" + inside + "</a>";
            return query;
        }
        // END


        /**
         * Generate the html form code for the object given in this class
         * @return the code html for the form in String
         */
        public String htmlForm() throws Exception {
            setHtmlForm();
            String valiny = "";
            String[] type = getTypeForm();
            for (int i = 0; i < type.length; i++) {
                valiny += objectForm[i];
            }
            valiny += "<input type='hidden' name='class' value='" + object.getClass().getName() + "'>";
            valiny += "<br><br><input type='submit' value='Send'>\n";
            return valiny;
        }
        // END



        /**
         * Hide a specific field in the html form generated by the object in this class
         * @param field the name of the field in this class object
         * @param value the value of the hidden field
         */
        public void hideHtmlInput(String field, String value) throws Exception {
            setHtmlForm();
            Field[] f = object.getClass().getDeclaredFields();
            String ad = toRenisoratra(field, 0);
            String valiny = "<input type='hidden' name='" + ad + "' value='" + value + "'>";
            for (int i = 0; i < f.length; i++) {
                if (f[i].getName().equals(field)) {
                    setObjectForm(i, valiny);
                }
            }
        }
        // END

        /**
         * Set the type of the input html of a given field in this class object
         * @param field the field of the object in this class
         * @param type the type of the input
         */
        public void setType(String field, String type) throws Exception {
            setHtmlForm();
            Field[] f = object.getClass().getDeclaredFields();
            String ad = toRenisoratra(field, 0);
            String valiny = "<p>" + ad + ":</p><input type='" + type + "' name='" + ad + "'>";
            for (int i = 0; i < f.length; i++) {
                if (f[i].getName().equals(field)) {
                    setObjectForm(i, valiny);
                }
            }
        }
        // END

        /**
         * set the value of an input of the field in this class's object
         * @param f the field name
         * @param value the value of the field
         */
        public void setDefaultValueInput(String f, Object value) throws Exception {
            setHtmlForm();
            String[] func = toFunction(object);
            for (int i = 0; i < func.length; i++) {
                if (f.equals(func[i])) {
                    String o = objectForm[i].substring(0, objectForm[i].length() - 2);
                    String rep = value.toString();
                    if (value instanceof Date) {
                        rep = convertToString((Date) value, false);
                    }
                    o += " value='" + rep + "'>";
                    setObjectForm(i, o);
                    break;
                }
            }
        }
        // END

        /**
         * list the input having the field name
         * @param f the name of the field in this class's object
         * @param values the values in array String of the option
         */
        public void listThisInput(String f, String... values) throws Exception {
            setHtmlForm();
            String[] func = toFunction(object);
            for (int i = 0; i < func.length; i++) {
                if (toRenisoratra(f, 0).equals(func[i])) {
                    objectForm[i] = "<p>" + func[i] + " :</p><select name='" + func[i] + "'>\n";
                    for (int x = 0; x < values.length; x++) {
                        objectForm[i] += "<option value='" + values[x] + "'>" + values[x] + "</option>\n";
                    }
                    objectForm[i] += "</select>";
                }
            }
        }
        // END


    /// END









    /// CONCERNING THE GENERALISATION IN GENERAL

         /**
          * get all the fields's name for only with specific types
          * @param o the object to get fields of
          * @return the names of all the fields
          */
        public static String[] toFunction(Object o) throws Exception {
            Field[] f = o.getClass().getDeclaredFields();
            String[] valiny = new String[f.length];
            for (int i = 0; i < f.length; i++) {
                if (f[i].getType().getSimpleName().equals("double") || f[i].getType().getSimpleName().equals("int")
                        || f[i].getType().getSimpleName().equals("String") ||
                        f[i].getType().getSimpleName().equals("float") || f[i].getType().getSimpleName().equals("Date")) {
                    String name = f[i].getName();
                    name = toRenisoratra(name, 0);
                    valiny[i] = name;
                }
                else
                    throw new Exception("There is ");
            }
            return valiny;
        }

        /**
         * get the getter name function of an Object
         * @param o the object to get the fields of
         * @return array of String of the names of all the methods in getters
         */
        public static String[] toFunctionGet(Object o) throws Exception {
            String[] s = toFunction(o);
            String[] get = new String[s.length];
            for (int i = 0; i < s.length; i++) {
                get[i] = "get";
                get[i] += s[i];
            }
            return get;
        }

        /**
         * GET IN STRING AN HTML TABLE FROM THE VALUES OF AN OBJECT
         * @return the html code of a table
         * @throws Exception
         */
        public String tabHtml() throws Exception {
            Object o = this.object;
            String[] func = toFunction(o);
            String[] get = toFunctionGet(o);
            String valiny = "<table border=1>\n";
            for (int i = 0; i < func.length; i++) {
                valiny += "<th>" + func[i] + "</th>";
            }
            for (int i = 0; i < get.length; i++) {
                Method m = o.getClass().getMethod(get[i]);
                valiny += "<td>" + m.invoke(o) + "</td>";
            }
            valiny += "</table>";
            return valiny;
        }
        // END
    /// END





    /// UTIL FUNCTIONS

        /**
         * MAKE UPPERCASE IN AN EXACT INDICE OF A STRING
         * @param a the String to make uppercase
         * @param ind the indice of the String to make uppercase
         * @return
         */
        public static String toRenisoratra(String a, int ind) {
            String upper = a.substring(ind, ind + 1).toUpperCase();
            String q = "";
            for (int i = 0; i < a.length(); i++) {
                if (i == ind) {
                    q = q.concat(upper);
                    i++;
                }
                try {
                    q = q.concat(a.substring(i, i + 1));
                } catch (Exception e) {
                }
            }
            return q;
        }
        // END
        
        /**
         * make the getters and setters of a given fields
         * @param fields the names of the fields
         * @return
         */
        static String makeFunction(String[]... fields) {
            String query = "";
            for (int i = 0; i < fields[1].length; i++) {
                query += "\n    public " + fields[0][i] + " get" + toRenisoratra(fields[1][i], 0) + "(){return " +
                        fields[1][i] + ";" + "}\n";
                query += "    public void set" + toRenisoratra(fields[1][i], 0) + "(" + fields[0][i]
                        + " temp)throws Exception{\n        " + fields[1][i] + "=temp" +
                        ";" + "\n    }\n";
            }
            return query;
        }
        

        /**
         * create an automatic class of given name, package 
         * @param name name of the new class
         * @param pack the package name of the new class
         * @param fields all the fields's names
         */
        public static void createClass(String name, String pack, String[]... fields) {
            String query = "";
            name = toRenisoratra(name.toLowerCase(), 0);
            if (!pack.equals(""))
                query = "package " + pack + ";\n" + "import java.util.*;\nimport java.sql.*;\n";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".java"));
                query += "public class " + name + "{\n";
                for (int i = 0; i < fields[1].length; i++) {
                    query += "    " + fields[0][i] + " " + fields[1][i] + ";\n";
                }
                query += makeFunction(fields);
                query += "}";
                writer.write(query);
                writer.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    /// END







    /// CONCERNING PANELS

        /**
         * GENERATES A PANEL FORM FROM this object
         * @return the panel of this object
         * @throws Exception
         */
        public JPanel panelAuto() throws Exception {
            Object o = this.object;
            JPanel p = new JPanel();
            p.setVisible(true);
            p.setSize(1200, 800);
            JLabel[] lla = new JLabel[o.getClass().getDeclaredFields().length];
            JTextField[] lt = new JTextField[o.getClass().getDeclaredFields().length];
            String[] s = toFunction(o);
            String[] get = toFunctionGet(o);
            for (int i = 0; i < lt.length; i++) {
                lla[i] = new JLabel(s[i]);
                lt[i] = new JTextField();
                Method m = o.getClass().getMethod(get[i]);
                lt[i].setText(String.valueOf(m.invoke(o)));
                p.add(lla[i]);
                p.add(lt[i]);
            }
            JButton ok = new JButton("OK");
            p.add(ok);
            return p;
        }
        // END

        /**
         * 
         * @param lohateny the title of the table
         * @param col all the columns of the table in String
         * @param rows all the rows of the table in String
         * @return the table in person
         */
        public static JPanel makeTable(String lohateny, String[] col, Object[]... rows) {
            JLabel title = new JLabel(lohateny.toUpperCase());
            JPanel valiny = new JPanel();
            JTable table = new JTable(rows, col);
            JScrollPane scroll = new JScrollPane(table);
            valiny.setLayout(new BoxLayout(valiny, BoxLayout.Y_AXIS));
            valiny.setSize(1000, 600);
            valiny.add(title);
            valiny.add(scroll);
            return valiny;
        }
    /// END

    // GET THE DATE FOR NOW IN STRING
    public static String getNow() {
        return new Date().toString();
    }
    // END


    /// ERRORS

        /**
         * throw something if the give Strings are null or empty
         * @param col all the String to verify
         */
        public static void emptyErrors(ArrayList<String> col) throws Exception {
            for (String val : col) {
                if (val.equals("") || val == null)
                    throw new Exception("Complete all the form before continuing");
            }
        }

        /**
         * 
         * @param date the date to begin
         * @param mineur tell the user if the person is date for age or not(minor or major)
         */
        public static void getDateError(String date, boolean mineur) throws Exception {
            Date now = new Date();
            Date daty = convertToDate(date, false);
            if (mineur && now.getYear() - daty.getYear() < 18) {
                throw new Exception("Impossible si mineur");
            }
        }
    /// END






    /// CONCERNING STRINGS
    
        public static String isIn(String container, String value) throws Exception {
            int count = container.length();
            String valiny = new String("");
            String element;
            for (int i = 0; i < count; i++) {
                try {
                    element = container.substring(i, i + value.length());
                    if (element.toLowerCase().equals(value.toLowerCase())) {
                        return container;
                    }
                } catch (Exception e) {
                    break;
                }
            }
            return valiny;
        }

        public static List<String> getIn(ArrayList<String> container, String value) {
            List<String> valiny = new ArrayList<>();
            String in = new String("");
            for (String string : container) {
                try {
                    in = isIn(string, value);
                    if (!in.equals(""))
                        valiny.add(in);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return valiny;
        }
    /// END






    /// CONCERNING ANNOTATION IN JAVA
        /**
         * get all the fields containing a specific annotation
         * @param <T> the generic class of the object containing the fields to verify
         * @param classname the class with the generic
         * @param value the class of the annotation to verify
         * @return all the fields to contain the annotation specified(value)
         */
        public static <T> List<Field> getFieldsAnnoted(Class<T> classname, Class value) throws Exception {
            List<Field> valiny = new ArrayList<>();
            Field[] fields = classname.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(value)) {
                    valiny.add(field);
                }
            }
            return valiny;
        }
    /// END
}

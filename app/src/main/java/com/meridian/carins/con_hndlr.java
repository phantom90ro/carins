package com.meridian.carins;

public class con_hndlr {
    public void mysql() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        thrd1 = new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {

                    }
                    if (con == null) {
                        try {
                            con = DriverManager.getConnection("jdbc:mysql://192.168.1.45:3306/deneme", "ali", "12345");

                        } catch (SQLException e) {
                            e.printStackTrace();
                            con = null;
                        }

                        if ((thrd2 != null) && (!thrd2.isAlive()))
                            thrd2.start();

                    }
                }

            }
        });
        if ((thrd1 != null) && (!thrd1.isAlive())) thrd1.start();

        thrd2 = new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {

                    if (con != null) {
                        try {
                            //   con = DriverManager.getConnection("jdbc:mysql://192.168.1.45:3306/deneme", "ali", "12345");
                            Statement st = con.createStatement();
                            String ali = "'fff'";
                            st.execute("INSERT INTO deneme (name) VALUES(" + ali + ")");
                            //  ResultSet rs = st.executeQuery("select * from deneme");
                            //  ResultSetMetaData rsmd = rs.getMetaData();
                            //  String result = new String();


                            //  while (rs.next()) {
                            //      result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                            //       result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";


                            //   }

                        } catch (SQLException e) {
                            e.printStackTrace();
                            con = null;
                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });


    }
}

package geoprocessing.wps4m.data;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import geoprocessing.wps4m.client.TimeConverter;

public class DataQuery {
	private Connection connection;
	private String format="yyyy/M/d hh:mm";
    private Connection connect() {
    	URL resource = DataQuery.class.getClassLoader().getResource("data.db");
		String path = resource.getPath();

    	String url = "jdbc:sqlite:"+path;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Observation select(Calendar time) {
        
    	if(this.connection == null)
    		this.connection = this.connect();
    	
    	String timeStr = TimeConverter.calendar2Str(time, format);
    	String sql = "SELECT * FROM data where TIME = " + "'"+timeStr+"'"; //  2006/1/8 12:00
        
        try {
           
            Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
                double prec = rs.getDouble("Precipitation");
                double pet = rs.getDouble("Evapotranspiration");
                Observation observation = new Observation(prec, pet);
                return observation;
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void closeConn() {
    	if(this.connection!=null)
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }
    public class Observation {
    	double precipitation,evapotranspiration;
    	public Observation(double precipitation, double pet) {
    		this.precipitation = precipitation;
    		this.evapotranspiration = pet;
    	}
    	
    	public double getPrecipitation() {
    		return this.precipitation;
    	}
    	
    	public double getEvapotranspiration() {    
    		return this.evapotranspiration;
    	}
    	
    }
}

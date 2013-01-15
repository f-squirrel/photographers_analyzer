
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileInputStream;
import java.util.Properties;

class Config {
    private static Config instance = null;
    private Properties prop = null;
    FilenameFilter filter = null;
    FilenameFilter dirFilter = null;

    private void loadProperties( String path ) throws Exception {
        if( prop == null ) {
            prop = new Properties();
        }
        File dir = new File( path );

        filter = new FilenameFilter() { 
        	public boolean accept(File dir, String name) { return name.endsWith(".properties"); } 
        };
        dirFilter = new FilenameFilter() { 
            public boolean accept(File dir, String name) { return dir.isDirectory(); } 
        };

        String files[] = dir.list(filter);
        System.out.println( "files length:" + files.length );
        for (int i = 0; i < files.length; i++) {
//        for( String file:files ) {
            System.out.println( "1:" + files[i] );
            File localFile = new File( "config" + File.separator + files[i] );
            if( localFile.isDirectory() ) continue;
            FileInputStream fis = new FileInputStream( localFile.getAbsolutePath() );
            prop.load( fis );
            fis.close();
        }
    }

    public Config() throws Exception {
        loadProperties( "config" );
    }

    public static String getProperty( String propertyName ) throws Exception {
        if( instance == null ) {
            instance = new Config();
        }

        return instance.prop.getProperty( propertyName );
    }
    
    
}

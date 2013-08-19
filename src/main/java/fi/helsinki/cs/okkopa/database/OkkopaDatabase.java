package fi.helsinki.cs.okkopa.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.helsinki.cs.okkopa.model.QRCode;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OkkopaDatabase {

    private static Dao<QRCode, String> qrcodeDao;
    private static ConnectionSource connectionSource;
    private static boolean open = false;

    public static boolean isOpen() {
        return open;
    }

    @Autowired
    public OkkopaDatabase() throws SQLException {
        String databaseUrl = Settings.instance.getProperty("database.h2.url");
        String username = Settings.instance.getProperty("database.h2.user");
        String password = Settings.instance.getProperty("database.h2.password");

        // create a connection source to our database
        connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

        // instantiate the dao
        qrcodeDao = DaoManager.createDao(connectionSource, QRCode.class);

        // if you need to create the 'accounts' table make this call
        TableUtils.createTableIfNotExists(connectionSource, QRCode.class);
        open = true;
    }

    public static String getUserID(String qrcodeString) throws SQLException {
        QRCode qrCode = qrcodeDao.queryForId(qrcodeString);
        if (qrCode == null) {
            return null;
        }
        return qrCode.getUserId();
    }

    public static boolean addQRCode(String QRCode) throws SQLException {
        QRCode qrCode = new QRCode(QRCode, "");

        if (qrcodeDao.idExists(QRCode) == false) {
            qrcodeDao.createIfNotExists(qrCode);
            return true;
        }
        return false;
    }
    
    public static boolean QRCodeExists(String QRCode) throws SQLException {
        if(qrcodeDao.queryForId(QRCode) != null) {
            return true;
        }
        return false;
    }

    public static boolean addUSer(String QRCode, String UserId) throws SQLException {
        QRCode qrCode = qrcodeDao.queryForId(QRCode);
        
        if (qrCode.getUserId().equals("")) {
            qrCode = new QRCode(QRCode, UserId);
            qrcodeDao.update(qrCode);
            return true;
        }
        return false;
    }

    public static void closeConnectionSource() throws SQLException {
        connectionSource.close();
    }
}
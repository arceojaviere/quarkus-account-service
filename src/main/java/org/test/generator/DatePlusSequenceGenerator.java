package org.test.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class DatePlusSequenceGenerator implements IdentifierGenerator, Configurable{

    private String query;
    private String sequenceName;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Connection connection = session.connection();
        String id = null;
        Date today = Calendar.getInstance().getTime();
        StringBuilder builder = new StringBuilder(String.format("%ty%tm%td",today,today,today)); 
        builder.append("-");
     
        try{
            ResultSet rs = connection.createStatement().executeQuery(String.format(this.query,this.sequenceName));
            rs.next();
            builder.append(rs.getString(1));
        }catch(Throwable t){
            throw new HibernateException("Error occurred while getting the sequence value", t);
        }
               id = builder.toString();
        return id;
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
                this.sequenceName = params.getProperty("sequence");
                this.query = params.getProperty("sequenceQuery") != null ? params.getProperty("sequenceQuery") : "SELECT %s.nextval FROM dual";

            }
    
}

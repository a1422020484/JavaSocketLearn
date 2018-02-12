package nettyServer.util;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * @author yangxp
 */
public class RBConfigurationConverter extends AbstractSingleValueConverter {

    @Override
    public boolean canConvert(Class type) {
        return type.equals(RBConfiguration.class);
    }

    @Override
    public Object fromString(String str) {
        RBConfiguration rb = new RBConfiguration();
        rb.setKey(str);
        return rb;
    }
}

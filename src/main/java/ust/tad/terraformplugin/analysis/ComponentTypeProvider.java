package ust.tad.terraformplugin.analysis;

import java.util.ArrayList;
import java.util.List;

import ust.tad.terraformplugin.models.tadm.ComponentType;
import ust.tad.terraformplugin.models.tadm.InvalidPropertyValueException;
import ust.tad.terraformplugin.models.tadm.Property;
import ust.tad.terraformplugin.models.tadm.PropertyType;

public class ComponentTypeProvider {
    
    public static ComponentType createPhysicalNodeType() throws InvalidPropertyValueException {        
        List<Property> properties = createPropertiesForPhysicalNode();

        ComponentType physicalNodeType = new ComponentType();
        physicalNodeType.setName("physical_node");
        physicalNodeType.setProperties(properties);

        return physicalNodeType;
    }

    public static List<Property> createPropertiesForPhysicalNode() throws InvalidPropertyValueException {
        List<Property> properties = new ArrayList<>();

        Property cpuCount = new Property();
        cpuCount.setKey("cpu_count");
        cpuCount.setType(PropertyType.INTEGER);

        Property ram = new Property();
        ram.setKey("ram_GiB");
        ram.setType(PropertyType.INTEGER);

        Property storage = new Property();
        storage.setKey("storage_GiB");
        storage.setType(PropertyType.INTEGER);
        
        properties.add(cpuCount);
        properties.add(ram);
        properties.add(storage);

        return properties;
    }
}

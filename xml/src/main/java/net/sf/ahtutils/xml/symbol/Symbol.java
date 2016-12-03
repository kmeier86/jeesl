
package net.sf.ahtutils.xml.symbol;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.sf.ahtutils.xml.status.Styles;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://ahtutils.aht-group.com/status}styles"/&gt;
 *         &lt;element ref="{http://www.jeesl.org/symbol}colors"/&gt;
 *         &lt;element ref="{http://www.jeesl.org/symbol}sizes"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="sizeBorder" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "styles",
    "colors",
    "sizes"
})
@XmlRootElement(name = "symbol")
public class Symbol
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(namespace = "http://ahtutils.aht-group.com/status", required = true)
    protected Styles styles;
    @XmlElement(required = true)
    protected Colors colors;
    @XmlElement(required = true)
    protected Sizes sizes;
    @XmlAttribute(name = "size")
    protected Integer size;
    @XmlAttribute(name = "sizeBorder")
    protected Integer sizeBorder;

    /**
     * Gets the value of the styles property.
     * 
     * @return
     *     possible object is
     *     {@link Styles }
     *     
     */
    public Styles getStyles() {
        return styles;
    }

    /**
     * Sets the value of the styles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Styles }
     *     
     */
    public void setStyles(Styles value) {
        this.styles = value;
    }

    public boolean isSetStyles() {
        return (this.styles!= null);
    }

    /**
     * Gets the value of the colors property.
     * 
     * @return
     *     possible object is
     *     {@link Colors }
     *     
     */
    public Colors getColors() {
        return colors;
    }

    /**
     * Sets the value of the colors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Colors }
     *     
     */
    public void setColors(Colors value) {
        this.colors = value;
    }

    public boolean isSetColors() {
        return (this.colors!= null);
    }

    /**
     * Gets the value of the sizes property.
     * 
     * @return
     *     possible object is
     *     {@link Sizes }
     *     
     */
    public Sizes getSizes() {
        return sizes;
    }

    /**
     * Sets the value of the sizes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sizes }
     *     
     */
    public void setSizes(Sizes value) {
        this.sizes = value;
    }

    public boolean isSetSizes() {
        return (this.sizes!= null);
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSize(int value) {
        this.size = value;
    }

    public boolean isSetSize() {
        return (this.size!= null);
    }

    public void unsetSize() {
        this.size = null;
    }

    /**
     * Gets the value of the sizeBorder property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSizeBorder() {
        return sizeBorder;
    }

    /**
     * Sets the value of the sizeBorder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSizeBorder(int value) {
        this.sizeBorder = value;
    }

    public boolean isSetSizeBorder() {
        return (this.sizeBorder!= null);
    }

    public void unsetSizeBorder() {
        this.sizeBorder = null;
    }

}

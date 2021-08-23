package com.zpmc.ztos.infra.base.business.model;

import java.io.Serializable;

public class BentoNode implements Serializable {
    private String _name;
//    private final Collection<BentoAttribute> _attributes;
//    private final Collection<BentoNode> _childNodes;
//    private static final Logger LOGGER = Logger.getLogger(BentoNode.class);
//    private static final long MAX_ONE_BYTE_LENGTH = 252L;
//    private static final long MAX_THREE_BYTE_LENGTH = 65535L;
//    private static final long MAX_FIVE_BYTE_LENGTH = 0xFFFFFFFFL;
//    private static final int THREE_BYTE_LENGTH_INDICATOR_BYTE = 255;
//    private static final int FIVE_BYTE_LENGTH_INDICATOR_BYTE = 254;
//    private static final int NINE_BYTE_LENGTH_INDICATOR_BYTE = 253;
//    private static final String E_BENTO_NODE = "node";
//    private static final String A_BENTO_NODE_ID = "id";
//    public static final IBentoNodeFilter BENTO_NODE_FILTER_SKIP_ALL = new IBentoNodeFilter(){
//
//        @Override
//        public boolean acceptAttribute(@NotNull BentoAttribute inBentoAttribute) {
//            return false;
//        }
//
//        @Override
//        public boolean acceptNode(@NotNull BentoNode inBentoNode) {
//            return false;
//        }
//    };
//    public static final IBentoNodeFilter BENTO_NODE_FILTER_ACCEPT_ALL = new IBentoNodeFilter(){
//
//        @Override
//        public boolean acceptAttribute(@NotNull BentoAttribute inBentoAttribute) {
//            return true;
//        }
//
//        @Override
//        public boolean acceptNode(@NotNull BentoNode inBentoNode) {
//            return true;
//        }
//    };
//
//    public BentoNode() {
//        this._name = "uninitialized";
//        this._attributes = new ArrayList<BentoAttribute>();
//        this._childNodes = new ArrayList<BentoNode>();
//    }
//
//    public BentoNode(@NotNull String inName) {
//        this._name = inName;
//        this._attributes = new ArrayList<BentoAttribute>();
//        this._childNodes = new ArrayList<BentoNode>();
//    }
//
//    public BentoNode(@NotNull DataInput inStream) throws IOException {
//        this._attributes = new ArrayList<BentoAttribute>();
//        this._childNodes = new ArrayList<BentoNode>();
//        this.readFromStream(inStream);
//    }
//
//    public void addChildNode(@NotNull BentoNode inChild) {
//        this._childNodes.add(inChild);
//    }
//
//    @NotNull
//    public BentoNode addNewChildNode(@NotNull String inName) {
//        BentoNode child = new BentoNode(inName);
//        this._childNodes.add(child);
//        return child;
//    }
//
//    public void addByte(@NotNull String inName, byte inValue) {
//        this.addAttribute(new BentoS8(inName, inValue));
//    }
//
//    public void addUnsignedByte(@NotNull String inName, byte inValue) {
//        this.addAttribute(new BentoU8(inName, inValue));
//    }
//
//    public void addShort(@NotNull String inName, short inValue) {
//        this.addAttribute(new BentoS16(inName, inValue));
//    }
//
//    public void addUnsignedShort(@NotNull String inName, short inValue) {
//        this.addAttribute(new BentoU16(inName, inValue));
//    }
//
//    public void addInteger(@NotNull String inName, int inValue) {
//        this.addAttribute(new BentoS32(inName, inValue));
//    }
//
//    public void addUnsignedInteger(@NotNull String inName, int inValue) {
//        this.addAttribute(new BentoU32(inName, inValue));
//    }
//
//    public void addLong(@NotNull String inName, long inValue) {
//        this.addAttribute(new BentoS64(inName, inValue));
//    }
//
//    public void addUnsignedLong(@NotNull String inName, long inValue) {
//        this.addAttribute(new BentoU64(inName, inValue));
//    }
//
//    public void addBoolean(@NotNull String inName, boolean inValue) {
//        this.addAttribute(new BentoBoolean(inName, inValue));
//    }
//
//    public void addString(@NotNull String inName, @NotNull String inValue) {
//        this.addAttribute(new BentoString(inName, inValue));
//    }
//
//    public void addStringArray(@NotNull String inName, @NotNull List<String> inValue) {
//        this.addAttribute(new BentoStringArray(inName, inValue));
//    }
//
//    public void addDouble(@NotNull String inName, double inValue) {
//        this.addAttribute(new BentoDouble(inName, inValue));
//    }
//
//    public void addFloat(@NotNull String inName, float inValue) {
//        this.addAttribute(new BentoFloat(inName, inValue));
//    }
//
//    public void addChar(@NotNull String inName, char inValue) {
//        this.addAttribute(new BentoChar(inName, inValue));
//    }
//
//    public void addInstant(@NotNull String inName, @NotNull Date inValue) {
//        this.addAttribute(new BentoInstant(inName, inValue));
//    }
//
//    public void addInstantArray(@NotNull String inName, @NotNull List<Long> inValue) {
//        this.addAttribute(new BentoInstantArray(inName, inValue));
//    }
//
//    public void addDuration(@NotNull String inName, long inValue) {
//        this.addAttribute(new BentoDuration(inName, inValue));
//    }
//
//    public void addColor(@NotNull String inName, @NotNull Color inValue) {
//        this.addAttribute(new BentoColor(inName, inValue));
//    }
//
//    public void addPoint(@NotNull String inName, @NotNull Point inValue) {
//        this.addAttribute(new BentoPoint(inName, inValue));
//    }
//
//    public void addPoint3D(@NotNull String inName, @NotNull Point3D inValue) {
//        this.addAttribute(new BentoPoint3D(inName, inValue));
//    }
//
//    public void addRect(@NotNull String inName, @NotNull Rect inValue) {
//        this.addAttribute(new BentoRect(inName, inValue));
//    }
//
//    public void addSize(@NotNull String inName, @NotNull Size inValue) {
//        this.addAttribute(new BentoSize(inName, inValue));
//    }
//
//    public void addPolygon(@NotNull String inName, @NotNull List<Point> inValue) {
//        this.addAttribute(new BentoPolygon(inName, inValue));
//    }
//
//    public void writeToStream(@NotNull DataOutput inOutputStream) throws IOException {
//        long contentSize = this.calculateContentSize();
//        BentoNode.writeLengthToStream(inOutputStream, contentSize);
//        int numAttributes = this._attributes.size();
//        inOutputStream.writeInt(numAttributes);
//        int numChildNodes = this._childNodes.size();
//        inOutputStream.writeInt(numChildNodes);
//        BentoNode.writeDynamicLengthString(inOutputStream, this._name);
//        for (BentoAttribute attribute : this._attributes) {
//            attribute.writeToStream(inOutputStream);
//        }
//        for (BentoNode childNode : this._childNodes) {
//            childNode.writeToStream(inOutputStream);
//        }
//    }
//
//    public void writeToBentoTextStream(@NotNull DataOutput inOutputStream) throws IOException {
//        inOutputStream.writeBytes("{ \"");
//        inOutputStream.writeBytes(this._name);
//        inOutputStream.writeBytes("\" ");
//        for (BentoAttribute attribute : this._attributes) {
//            attribute.writeToBentoTextStream(inOutputStream);
//            inOutputStream.writeBytes(" ");
//        }
//        for (BentoNode childNode : this._childNodes) {
//            childNode.writeToBentoTextStream(inOutputStream);
//            inOutputStream.writeBytes(" ");
//        }
//        inOutputStream.writeBytes("}");
//    }
//
//    @NotNull
//    public String writeToBentoTextString() {
//        try {
//            MemoryStream tempBuffer = new MemoryStream();
//            this.writeToBentoTextStream(tempBuffer);
//            tempBuffer.prepareToRead();
//            byte[] theText = new byte[tempBuffer.size()];
//            tempBuffer.readFully(theText);
//            String result = new String(theText);
//            return result;
//        }
//        catch (IOException ex) {
//            LOGGER.error((Object)("writeToBentoTextString: failed with: " + ex));
//            return "";
//        }
//    }
//
//    public final void readFromStream(@NotNull DataInput inStream) throws IOException {
//        int i;
//        BentoNode.readLengthFromStream(inStream);
//        int numAttributes = inStream.readInt();
//        int numChildNodes = inStream.readInt();
//        this._name = BentoNode.readDynamicLengthString(inStream);
//        for (i = 0; i < numAttributes; ++i) {
//            this.addAttribute(BentoAttribute.newObjectFromStream(inStream));
//        }
//        for (i = 0; i < numChildNodes; ++i) {
//            this.addChildNode(new BentoNode(inStream));
//        }
//    }
//
//    @NotNull
//    public static BentoNode createFromElement(@NotNull Element inElement) {
//        String id = inElement.getAttributeValue(A_BENTO_NODE_ID);
//        BentoNode node = new BentoNode(id);
//        List childElements = inElement.getChildren();
//        if (childElements != null) {
//            for (Element childElement : childElements) {
//                if (E_BENTO_NODE.equals(childElement.getName())) {
//                    BentoNode childNode = BentoNode.createFromElement(childElement);
//                    node.addChildNode(childNode);
//                    continue;
//                }
//                BentoAttribute attribute = BentoAttribute.createFromElement(childElement);
//                node.addAttribute(attribute);
//            }
//        }
//        return node;
//    }
//
//    @NotNull
//    public BentoNode filter(@NotNull IBentoNodeFilter inBentoNodeFilter) {
//        BentoNode bentoNode = new BentoNode(this._name);
//        for (BentoAttribute attribute : this._attributes) {
//            if (!inBentoNodeFilter.acceptAttribute(attribute)) continue;
//            bentoNode.addAttribute(attribute);
//        }
//        for (BentoNode childNode : this._childNodes) {
//            if (!inBentoNodeFilter.acceptNode(childNode)) continue;
//            bentoNode.addChildNode(childNode);
//        }
//        return bentoNode;
//    }
//
//    @NotNull
//    public Element toXmlElement() {
//        Element element = new Element(E_BENTO_NODE);
//        if (StringUtils.isNotEmpty((String)this._name)) {
//            element.setAttribute(A_BENTO_NODE_ID, this._name);
//        }
//        for (BentoAttribute attribute : this._attributes) {
//            Element attributeElement = attribute.toXmlElement();
//            element.addContent((Content)attributeElement);
//        }
//        for (BentoNode childNode : this._childNodes) {
//            Element childNodeElement = childNode.toXmlElement();
//            element.addContent((Content)childNodeElement);
//        }
//        return element;
//    }
//
//    public Map toMap() {
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        for (BentoAttribute attribute : this._attributes) {
//            resultMap.put(attribute.getName(), attribute.getValueAsObject());
//        }
//        for (BentoNode childNode : this._childNodes) {
//            Map childMap = childNode.toMap();
//            resultMap.put(childNode.getName(), childMap);
//        }
//        return resultMap;
//    }
//
//    @NotNull
//    public Collection<BentoNode> getNodes() {
//        return Collections.unmodifiableCollection(this._childNodes);
//    }
//
//    @NotNull
//    public Iterator<BentoNode> getNodesIterator() {
//        return this._childNodes.iterator();
//    }
//
//    public int getNodesCount() {
//        return this._childNodes.size();
//    }
//
//    @NotNull
//    public Collection<BentoAttribute> getAttributes() {
//        return Collections.unmodifiableCollection(this._attributes);
//    }
//
//    @NotNull
//    public Iterator<BentoAttribute> getAttributesIterator() {
//        return this._attributes.iterator();
//    }
//
//    public int getAttributesCount() {
//        return this._attributes.size();
//    }
//
//    @Nullable
//    public BentoNode findNode(@Nullable String inName) {
//        for (BentoNode child : this._childNodes) {
//            if (!child.getName().equals(inName)) continue;
//            return child;
//        }
//        return null;
//    }
//
//    @Nullable
//    public BentoAttribute findAttribute(@Nullable String inName) {
//        for (BentoAttribute attribute : this._attributes) {
//            if (!attribute.getName().equals(inName)) continue;
//            return attribute;
//        }
//        return null;
//    }
//
//    @Nullable
//    public BentoNode findNode(@NotNull String inName, @NotNull String inAttributeName, @NotNull String inDataType) {
//        for (BentoNode child : this._childNodes) {
//            if (!child.getName().equals(inName) || child.findAttribute(inAttributeName, inDataType) == null) continue;
//            return child;
//        }
//        return null;
//    }
//
//    public byte getByte(@NotNull String inAttributeName, byte inDefaultValue) {
//        BentoS8 attribute = (BentoS8)this.findAttribute(inAttributeName, "vs_8");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public byte getByte(@NotNull String inAttributeName) {
//        BentoS8 attribute = (BentoS8)this.findAttribute(inAttributeName, "vs_8");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vs_8 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public byte getUnsignedByte(@NotNull String inAttributeName, byte inDefaultValue) {
//        BentoU8 attribute = (BentoU8)this.findAttribute(inAttributeName, "vu_8");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public byte getUnsignedByte(@NotNull String inAttributeName) {
//        BentoU8 attribute = (BentoU8)this.findAttribute(inAttributeName, "vu_8");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vu_8 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public short getShort(@NotNull String inAttributeName, short inDefaultValue) {
//        BentoS16 attribute = (BentoS16)this.findAttribute(inAttributeName, "vs16");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public short getShort(@NotNull String inAttributeName) {
//        BentoS16 attribute = (BentoS16)this.findAttribute(inAttributeName, "vs16");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vs16 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public short getUnsignedShort(@NotNull String inAttributeName, short inDefaultValue) {
//        BentoU16 attribute = (BentoU16)this.findAttribute(inAttributeName, "vu16");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public short getUnsignedShort(@NotNull String inAttributeName) {
//        BentoU16 attribute = (BentoU16)this.findAttribute(inAttributeName, "vu16");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vu16 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public int getInteger(@NotNull String inAttributeName, int inDefaultValue) {
//        BentoS32 attribute = (BentoS32)this.findAttribute(inAttributeName, "vs32");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public int getInteger(@NotNull String inAttributeName) {
//        BentoS32 attribute = (BentoS32)this.findAttribute(inAttributeName, "vs32");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vs32 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public int getUnsignedInteger(@NotNull String inAttributeName, int inDefaultValue) {
//        BentoU32 attribute = (BentoU32)this.findAttribute(inAttributeName, "vu32");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public int getUnsignedInteger(@NotNull String inAttributeName) {
//        BentoU32 attribute = (BentoU32)this.findAttribute(inAttributeName, "vu32");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vu32 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public long getLong(@NotNull String inAttributeName, long inDefaultValue) {
//        BentoS64 attribute = (BentoS64)this.findAttribute(inAttributeName, "vs64");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public long getLong(@NotNull String inAttributeName) {
//        BentoS64 attribute = (BentoS64)this.findAttribute(inAttributeName, "vs64");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vs64 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public long getUnsignedLong(@NotNull String inAttributeName, long inDefaultValue) {
//        BentoU64 attribute = (BentoU64)this.findAttribute(inAttributeName, "vu64");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public long getUnsignedLong(@NotNull String inAttributeName) {
//        BentoU64 attribute = (BentoU64)this.findAttribute(inAttributeName, "vu64");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vu64 name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public boolean getBoolean(@NotNull String inAttributeName, boolean inDefaultValue) {
//        BentoBoolean attribute = (BentoBoolean)this.findAttribute(inAttributeName, "bool");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public boolean getBoolean(@NotNull String inAttributeName) {
//        BentoBoolean attribute = (BentoBoolean)this.findAttribute(inAttributeName, "bool");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type bool name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public String getString(@NotNull String inAttributeName, @Nullable String inDefaultValue) {
//        BentoString attribute = (BentoString)this.findAttribute(inAttributeName, "vstr");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public String getString(@NotNull String inAttributeName) {
//        BentoString attribute = (BentoString)this.findAttribute(inAttributeName, "vstr");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vstr name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public List<String> getStringArray(@NotNull String inAttributeName, @Nullable List<String> inDefaultValue) {
//        BentoStringArray attribute = (BentoStringArray)this.findAttribute(inAttributeName, "vsta");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public List<String> getStringArray(@NotNull String inAttributeName) {
//        BentoStringArray attribute = (BentoStringArray)this.findAttribute(inAttributeName, "vsta");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type vsta name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public double getDouble(@NotNull String inAttributeName, double inDefaultValue) {
//        BentoDouble attribute = (BentoDouble)this.findAttribute(inAttributeName, "doub");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public double getDouble(@NotNull String inAttributeName) {
//        BentoDouble attribute = (BentoDouble)this.findAttribute(inAttributeName, "doub");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type doub name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public float getFloat(@NotNull String inAttributeName, float inDefaultValue) {
//        BentoFloat attribute = (BentoFloat)this.findAttribute(inAttributeName, "flot");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public float getFloat(@NotNull String inAttributeName) {
//        BentoFloat attribute = (BentoFloat)this.findAttribute(inAttributeName, "flot");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type flot name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public char getChar(@NotNull String inAttributeName, char inDefaultValue) {
//        BentoChar attribute = (BentoChar)this.findAttribute(inAttributeName, "char");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public char getChar(@NotNull String inAttributeName) {
//        BentoChar attribute = (BentoChar)this.findAttribute(inAttributeName, "char");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type char name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Date getInstant(@NotNull String inAttributeName, @Nullable Date inDefaultValue) {
//        BentoInstant attribute = (BentoInstant)this.findAttribute(inAttributeName, "inst");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return BentoInstant.convertMillisToDate(attribute.getValue());
//    }
//
//    @Nullable
//    public Date getInstant(@NotNull String inAttributeName) {
//        BentoInstant attribute = (BentoInstant)this.findAttribute(inAttributeName, "inst");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type inst name " + inAttributeName + " not found.");
//        }
//        return BentoInstant.convertMillisToDate(attribute.getValue());
//    }
//
//    @Nullable
//    public List<Long> getInstantArray(@NotNull String inAttributeName, @Nullable List<Long> inDefaultValue) {
//        BentoInstantArray attribute = (BentoInstantArray)this.findAttribute(inAttributeName, "insa");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public List<Long> getInstantArray(@NotNull String inAttributeName) {
//        BentoInstantArray attribute = (BentoInstantArray)this.findAttribute(inAttributeName, "insa");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type insa name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    public long getDuration(@NotNull String inAttributeName, long inDefaultValue) {
//        BentoDuration attribute = (BentoDuration)this.findAttribute(inAttributeName, "dura");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    public long getDuration(@NotNull String inAttributeName) {
//        BentoDuration attribute = (BentoDuration)this.findAttribute(inAttributeName, "dura");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type dura name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public Color getColor(@NotNull String inAttributeName) {
//        BentoColor attribute = (BentoColor)this.findAttribute(inAttributeName, "rgba");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type rgba name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Color getColor(@NotNull String inAttributeName, @Nullable Color inDefaultValue) {
//        BentoColor attribute = (BentoColor)this.findAttribute(inAttributeName, "rgba");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public Point getPoint(@NotNull String inAttributeName) {
//        BentoPoint attribute = (BentoPoint)this.findAttribute(inAttributeName, "pt_d");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type pt_d name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Point getPoint(@NotNull String inAttributeName, @Nullable Point inDefaultValue) {
//        BentoPoint attribute = (BentoPoint)this.findAttribute(inAttributeName, "pt_d");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public Point3D getPoint3D(@NotNull String inAttributeName) {
//        BentoPoint3D attribute = (BentoPoint3D)this.findAttribute(inAttributeName, "pt3d");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type pt3d name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Point3D getPoint3D(@NotNull String inAttributeName, @Nullable Point3D inDefaultValue) {
//        BentoPoint3D attribute = (BentoPoint3D)this.findAttribute(inAttributeName, "pt3d");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public Rect getRect(@NotNull String inAttributeName) {
//        BentoRect attribute = (BentoRect)this.findAttribute(inAttributeName, "recd");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type recd name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Rect getRect(@NotNull String inAttributeName, @Nullable Rect inDefaultValue) {
//        BentoRect attribute = (BentoRect)this.findAttribute(inAttributeName, "recd");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public Size getSize(@NotNull String inAttributeName) {
//        BentoSize attribute = (BentoSize)this.findAttribute(inAttributeName, "sizd");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type sizd name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public Size getSize(@NotNull String inAttributeName, @Nullable Size inDefaultValue) {
//        BentoSize attribute = (BentoSize)this.findAttribute(inAttributeName, "sizd");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public List<Point> getPolygon(@NotNull String inAttributeName) {
//        BentoPolygon attribute = (BentoPolygon)this.findAttribute(inAttributeName, "pold");
//        if (attribute == null) {
//            throw BizFailure.create("Attribute type pold name " + inAttributeName + " not found.");
//        }
//        return attribute.getValue();
//    }
//
//    @Nullable
//    public List<Point> getPolygon(@NotNull String inAttributeName, @Nullable List<Point> inDefaultValue) {
//        BentoPolygon attribute = (BentoPolygon)this.findAttribute(inAttributeName, "pold");
//        if (attribute == null) {
//            return inDefaultValue;
//        }
//        return attribute.getValue();
//    }
//
//    @NotNull
//    public String getName() {
//        return this._name;
//    }
//
//    @NotNull
//    public String getPrintStreamLayout() {
//        StringBuffer buf = new StringBuffer();
//        this.printStreamLayout(buf, 0);
//        return buf.toString();
//    }
//
//    public void printStreamLayout(@NotNull StringBuffer inOutBuffer) {
//        this.printStreamLayout(inOutBuffer, 0);
//    }
//
//    private void printStreamLayout(@NotNull StringBuffer inOutBuffer, int inIndentLevel) {
//        BentoNode.printIndent(inOutBuffer, inIndentLevel);
//        inOutBuffer.append("node '").append(this._name).append("'\n");
//        for (BentoAttribute attribute : this._attributes) {
//            attribute.printStreamLayout(inOutBuffer, inIndentLevel + 1);
//        }
//        for (BentoNode childNode : this._childNodes) {
//            childNode.printStreamLayout(inOutBuffer, inIndentLevel + 1);
//        }
//    }
//
//    protected static void printIndent(@NotNull StringBuffer inOutBuffer, int inIndentLevel) {
//        for (int i = 0; i < inIndentLevel; ++i) {
//            inOutBuffer.append("  ");
//        }
//    }
//
//    public long getSerializedSize() {
//        long serializedSize = this.calculateTotalSize();
//        return serializedSize;
//    }
//
//    private long calculateContentSize() {
//        long lengthOfCounters = 8L;
//        long lengthOfName = BentoNode.getBinaryStringLength(this._name);
//        long lengthOfAttributes = 0L;
//        for (BentoAttribute attribute : this._attributes) {
//            lengthOfAttributes += attribute.calculateTotalSize();
//        }
//        long lengthOfChildren = 0L;
//        for (BentoNode childNode : this._childNodes) {
//            lengthOfChildren += childNode.calculateTotalSize();
//        }
//        return 8L + lengthOfName + lengthOfAttributes + lengthOfChildren;
//    }
//
//    private long calculateTotalSize() {
//        long contentSize = this.calculateContentSize();
//        long lengthOfLength = BentoNode.getLengthOfLength(contentSize);
//        return lengthOfLength + contentSize;
//    }
//
//    private void addAttribute(@NotNull BentoAttribute inAttribute) {
//        this._attributes.add(inAttribute);
//    }
//
//    @Nullable
//    private BentoAttribute findAttribute(@NotNull String inAttributeName, @NotNull String inDataType) {
//        for (BentoAttribute attribute : this._attributes) {
//            if (!attribute.getName().equals(inAttributeName) || !attribute.getDataType().equals(inDataType)) continue;
//            return attribute;
//        }
//        return null;
//    }
//
//    @NotNull
//    public String toString() {
//        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
//        String xmlString = xout.outputString(this.toXmlElement());
//        return "\n" + xmlString;
//    }
//
//    protected static long readLengthFromStream(@NotNull DataInput inStream) throws IOException {
//        int lengthKind = inStream.readUnsignedByte();
//        if (lengthKind == 255) {
//            return inStream.readShort();
//        }
//        if (lengthKind == 254) {
//            return inStream.readInt();
//        }
//        if (lengthKind == 253) {
//            return inStream.readLong();
//        }
//        return lengthKind;
//    }
//
//    protected static void writeLengthToStream(@NotNull DataOutput inOutputStream, long inLength) throws IOException {
//        if (inLength <= 252L) {
//            inOutputStream.writeByte((int)inLength);
//        } else if (inLength <= 65535L) {
//            inOutputStream.writeByte(255);
//            inOutputStream.writeShort((int)inLength);
//        } else if (inLength <= 0xFFFFFFFFL) {
//            inOutputStream.writeByte(254);
//            inOutputStream.writeInt((int)inLength);
//        } else {
//            inOutputStream.writeByte(253);
//            inOutputStream.writeLong(inLength);
//        }
//    }
//
//    protected static long getLengthOfLength(long inLength) {
//        if (inLength <= 252L) {
//            return 1L;
//        }
//        if (inLength <= 65535L) {
//            return 3L;
//        }
//        if (inLength <= 0xFFFFFFFFL) {
//            return 5L;
//        }
//        return 9L;
//    }
//
//    @NotNull
//    protected static String readFourCharCodeFromStream(@NotNull DataInput inStream) throws IOException {
//        byte[] code = new byte[4];
//        inStream.readFully(code);
//        return new String(code);
//    }
//
//    protected static void writeFourCharCodeToStream(@NotNull DataOutput inOutputStream, @NotNull String inType) throws IOException {
//        int codeLength = inType.length();
//        byte[] bytes = inType.getBytes();
//        inOutputStream.write(bytes, 0, Math.min(4, codeLength));
//        for (int i = codeLength; i < 4; ++i) {
//            inOutputStream.writeByte(32);
//        }
//    }
//
//    @NotNull
//    protected static String readDynamicLengthString(@NotNull DataInput inStream, @NotNull String inCharsetName) throws IOException {
//        long length = BentoNode.readLengthFromStream(inStream);
//        if (length > Integer.MAX_VALUE) {
//            throw BizFailure.create("String with unsupported length > 2GB encountered in stream.");
//        }
//        byte[] bytes = new byte[(int)length];
//        inStream.readFully(bytes);
//        if (inCharsetName.isEmpty()) {
//            return new String(bytes, "UTF-8");
//        }
//        return new String(bytes, inCharsetName);
//    }
//
//    private static String preProcessEncoding(@NotNull byte[] inBytes, @NotNull String inCharsetName) throws CharacterCodingException, UnsupportedEncodingException {
//        Charset charset = Charset.forName(inCharsetName);
//        CharsetDecoder decoder = charset.newDecoder();
//        decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
//        decoder.onMalformedInput(CodingErrorAction.REPORT);
//        CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(inBytes));
//        return new String(charBuffer.toString().getBytes(), inCharsetName);
//    }
//
//    @NotNull
//    protected static String readDynamicLengthString(@NotNull DataInput inStream) throws IOException {
//        return BentoNode.readDynamicLengthString(inStream, "");
//    }
//
//    protected static void writeDynamicLengthString(@NotNull DataOutput inOutputStream, @NotNull String inString) throws IOException {
//        byte[] bytes = inString.getBytes("UTF-8");
//        BentoNode.writeLengthToStream(inOutputStream, bytes.length);
//        inOutputStream.write(bytes);
//    }
//
//    protected static long getBinaryStringLength(@NotNull String inString) {
//        long textLength = inString.length();
//        long lengthOfLength = BentoNode.getLengthOfLength(textLength);
//        return lengthOfLength + textLength;
//    }

}

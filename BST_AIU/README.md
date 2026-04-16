# Restaurant Management System 🍽️

## نظرة عامة | Overview

نظام إدارة شامل للمطاعم والحجوزات مبني بلغة Java مع واجهة رسومية احترافية.

A comprehensive Restaurant Management System built with Java and Swing GUI, featuring advanced data structures and sorting algorithms.

---

## المميزات الرئيسية | Main Features

### ✅ إدارة العملاء | Customer Management
- إضافة عملاء جدد مع معلومات كاملة (الاسم، الهاتف، البريد الإلكتروني، نوع العضوية)
- نظام نقاط الولاء التلقائي (10 نقاط لكل ضيف في الحجز)
- البحث عن عميل معين باستخدام رقم العميل
- حذف سجلات العملاء
- عرض جميع العملاء مع إمكانية الترتيب

### 🪑 إدارة الحجوزات | Reservation Management
- إنشاء حجوزات جديدة للعملاء
- التحقق التلقائي من وجود العميل قبل إنشاء الحجز
- تحديد رقم الطاولة وعدد الضيوف
- تتبع حالة الحجز (مؤكد، ملغي، مكتمل، لم يحضر)
- حذف الحجوزات
- ترتيب الحجوزات حسب التاريخ

### 📊 التقارير والإحصائيات | Reports & Statistics
- تقرير شامل عن العملاء (مرتب بالاسم أو الرقم)
- تقرير تفصيلي عن الحجوزات (مرتب بالتاريخ أو غير مرتب)
- تقرير ملخص للنظام بالكامل
- طباعة التقارير مباشرة
- حفظ التقارير كملفات نصية

### 🎨 واجهة مستخدم احترافية | Professional UI
- تصميم عصري مع شريط جانبي للتنقل
- وضع داكن/فاتح قابل للتبديل
- أزرار دائرية أنيقة
- ألوان متناسقة ومريحة للعين
- لوحة معلومات تفاعلية

---

## الهيكل التقني | Technical Architecture

### 📁 ملفات المشروع | Project Files

#### 1️⃣ **Main.java** (نقطة البداية)
**الوصف**: الملف الرئيسي الذي يبدأ تشغيل البرنامج.

**الكود الأساسي**:
```java
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RestaurantController().showMainWindow();
        });
    }
}
```

**الشرح**: 
- يستخدم `SwingUtilities.invokeLater()` لضمان تشغيل الواجهة الرسومية بشكل آمن
- ينشئ كائن من `RestaurantController` ويعرض النافذة الرئيسية

---

#### 2️⃣ **BSTNode.java** (عقدة الشجرة)
**الوصف**: يمثل عقدة واحدة في شجرة البحث الثنائية (Binary Search Tree).

**المكونات الرئيسية**:
```java
public class BSTNode<T extends Comparable<T>> {
    private T data;           // البيانات المخزنة
    private BSTNode<T> left;  // الفرع الأيسر
    private BSTNode<T> right; // الفرع الأيمن
}
```

**الشرح**:
- `T extends Comparable<T>`: يعني أن البيانات يجب أن تكون قابلة للمقارنة
- `left`: يشير للعقدة الأصغر
- `right`: يشير للعقدة الأكبر

---

#### 3️⃣ **BinarySearchTree.java** (شجرة البحث الثنائية)
**الوصف**: هيكل بيانات متقدم لتخزين العملاء بكفاءة عالية.

**المميزات**:
- **الإضافة (Insert)**: O(log n) في المتوسط
- **البحث (Search)**: O(log n) في المتوسط
- **الحذف (Delete)**: O(log n) في المتوسط
- **العرض المرتب (Inorder Traversal)**: O(n)

**الدوال الرئيسية**:

### 🔹 1. **إضافة عنصر (Insert)** - شرح مفصل

#### الكود:
```java
public void insert(T data) {
    root = insertRecursive(root, data);
}

private BSTNode<T> insertRecursive(BSTNode<T> node, T data) {
    // الحالة الأساسية: وصلنا لمكان فاضي
    if (node == null) {
        return new BSTNode<>(data);  // أنشئ عقدة جديدة هنا
    }
    
    // قارن القيمة الجديدة بالعقدة الحالية
    int comparison = data.compareTo(node.getData());
    
    if (comparison < 0) {
        // القيمة أصغر → روح يسار
        node.setLeft(insertRecursive(node.getLeft(), data));
    } else if (comparison > 0) {
        // القيمة أكبر → روح يمين
        node.setRight(insertRecursive(node.getRight(), data));
    }
    
    return node;  // ارجع العقدة الحالية
}
```

#### 📖 **شرح الفكرة خطوة بخطوة**:

**القاعدة الأساسية في BST**:
- **القيم الأصغر** → على اليسار (left)
- **القيم الأكبر** → على اليمين (right)

#### 🎯 **مثال عملي: إضافة عملاء**

لنفترض نضيف عملاء بأرقام: `C1000`, `C1001`, `C1002`, `C1003`, `C1004`

**الخطوة 1**: إضافة `C1000` (أول عنصر)
```
        C1000  ← root
```

**الخطوة 2**: إضافة `C1001`
```
        C1000
            \
            C1001
```

**الخطوة 3**: إضافة `C1002`
```
        C1000
            \
           C1001
              \
             C1002
```

**الخطوة 4**: إضافة `C1003`
```
        C1000
           \
           C1001
              \
             C1002
                \ 
                C1003
```

**الخطوة 5**: إضافة `C1004`
```
        C1000
           \
           C1001 
              \
              C1002
                \
                C1003
                  \
                  C1004
```

---

#### 4️⃣ **Customer.java** (نموذج العميل)
**الوصف**: يمثل بيانات عميل واحد في النظام.

**البيانات المخزنة**:
```java
public class Customer implements Comparable<Customer> {
    private String customerID;      // رقم العميل (فريد)
    private String name;            // الاسم
    private String phone;           // رقم الهاتف
    private String email;           // البريد الإلكتروني
    private String membershipType;  // نوع العضوية (Regular, Silver, Gold, Platinum)
    private int loyaltyPoints;      // نقاط الولاء
}
```

**الدوال المهمة**:
- `compareTo()`: للمقارنة بين العملاء بناءً على رقم العميل
- `addLoyaltyPoints()`: إضافة نقاط ولاء (10 نقاط لكل ضيف)
- Getters & Setters: للوصول وتعديل البيانات

**مثال على استخدام**:
```java
Customer customer = new Customer("C1001", "Ahmed Ali", "0123456789", "ahmed@email.com", "Gold");
```

---

#### 5️⃣ **Reservation.java** (نموذج الحجز)
**الوصف**: يمثل حجز طاولة لعميل في المطعم.

**البيانات المخزنة**:
```java
public class Reservation {
    private String reservationID;   // رقم الحجز
    private String customerID;      // رقم العميل
    private String tableNumber;     // رقم الطاولة
    private Date reservationDate;   // تاريخ ووقت الحجز
    private int numberOfGuests;     // عدد الضيوف
    private String status;          // الحالة (Confirmed, Cancelled, Completed, No-Show)
}
```

**الدوال المهمة**:
- `cancel()`: إلغاء الحجز
- `complete()`: تحديد الحجز كمكتمل
- `noShow()`: تسجيل عدم حضور العميل

**حالات الحجز**:
- **Confirmed**: مؤكد (الحالة الافتراضية)
- **Cancelled**: ملغي
- **Completed**: مكتمل

---

#### 6️⃣ **RestaurantController.java** (المتحكم الرئيسي)
**الوصف**: العقل المدبر للنظام - يربط بين البيانات والواجهة.

**المكونات الرئيسية**:
```java
public class RestaurantController {
    private BinarySearchTree<Customer> customers;        // شجرة العملاء
    private ArrayList<Reservation> reservations;         // قائمة الحجوزات
    private RestaurantView view;                         // الواجهة الرسومية
}
```

**الوظائف الأساسية**:

1. **إدارة العملاء**:
```java
addNewCustomer()        // إضافة عميل جديد
searchCustomer()        // البحث عن عميل
deleteCustomer()        // حذف عميل
getCustomerList()       // عرض جميع العملاء
```

2. **إدارة الحجوزات**:
```java
makeNewReservation()    // إنشاء حجز جديد (مع التحقق من وجود العميل)
searchReservation()     // البحث عن حجز
deleteReservation()     // حذف حجز
```

3. **الترتيب (Merge Sort)**:
```java
getCustomersSortedByName()      // ترتيب العملاء بالاسم
getReservationsSortedByDate()   // ترتيب الحجوزات بالتاريخ
```

**ميزة إضافية - نظام نقاط الولاء**:
```java
// في makeNewReservation()
customer.addLoyaltyPoints(numberOfGuests * 10);
// كل ضيف = 10 نقاط ولاء
```

**خوارزمية Merge Sort**:
- **التعقيد الزمني**: O(n log n) - ممتاز للبيانات الكبيرة
- **مستقرة**: تحافظ على ترتيب العناصر المتساوية
- **تستخدم لـ**: ترتيب العملاء بالاسم والحجوزات بالتاريخ

---

#### 7️⃣ **RestaurantView.java** (الواجهة الرسومية)
**الوصف**: الواجهة البصرية الكاملة للنظام - أكبر ملف (~950 سطر).

**المكونات الرئيسية**:

##### 🎨 **نظام الألوان**:
```java
// الوضع الفاتح
BACKGROUND_COLOR = Color(245, 245, 250)
SIDEBAR_COLOR = Color(255, 255, 255)
TEXT_COLOR = Color(40, 40, 40)

// الوضع الداكن
BACKGROUND_COLOR = Color(30, 30, 35)
SIDEBAR_COLOR = Color(40, 40, 45)
TEXT_COLOR = Color(220, 220, 220)
```

##### 📍 **الشريط الجانبي (Sidebar)**:
- عرض: 220 بكسل
- 4 أقسام رئيسية:
  1. **Dashboard**: لوحة المعلومات
  2. **Customers**: إدارة العملاء
  3. **Reservations**: إدارة الحجوزات
  4. **Reports**: التقارير

##### 🔘 **الأزرار الدائرية**:
- يستخدم `Graphics2D` لرسم أزرار بحواف دائرية (15px)
- `Anti-aliasing` لجعل الحواف ناعمة

##### 📊 **لوحة المعلومات (Dashboard)**:
```java
- إجمالي العملاء (Total Customers)
- إجمالي الحجوزات (Total Reservations)
- الحجوزات المؤكدة (Confirmed)
- الحجوزات الملغية (Cancelled)
- الحجوزات المكتملة (Completed)
```

##### 📝 **نماذج الإدخال**:
- **إضافة عميل**: 5 حقول (ID, Name, Phone, Email, Membership Type)
- **إضافة حجز**: 4 حقول (ID, Customer ID, Table Number, Number of Guests)
- **التحقق التلقائي**: يفحص وجود العميل قبل إنشاء الحجز

##### 📄 **التقارير**:
1. **تقرير العملاء**:
   - خيار 1: مرتب بالاسم (Merge Sort)
   - خيار 2: مرتب بالرقم (BST Inorder)

2. **تقرير الحجوزات**:
   - خيار 1: مرتب بالتاريخ (Merge Sort)
   - خيار 2: غير مرتب (Unsorted)

3. **التقرير الملخص**:
   - إحصائيات شاملة عن النظام

---

## كيفية التشغيل | How to Run

### المتطلبات | Requirements
```
- Java JDK 8 أو أحدث
- Windows/Linux/Mac OS
- 512MB RAM على الأقل
```

### خطوات التشغيل | Steps

#### الطريقة 1: من VSCode
```bash
1. افتح المشروع في VSCode
2. اضغط F5 أو اختر Run > Run Without Debugging
3. أو اضغط على زر ▶️ Run في أعلى اليمين
```

#### الطريقة 2: من Command Line
```bash
# الانتقال لمجلد المشروع
cd "E:\Java Project"

# التجميع
javac *.java

# التشغيل
java Main
```

#### الطريقة 3: من PowerShell
```powershell
e:; cd 'e:\Java Project'; javac *.java; java Main
```

---

## دليل الاستخدام | User Guide

### 1️⃣ إضافة عميل جديد
1. اذهب إلى قسم **Customers** من القائمة الجانبية
2. املأ الحقول: Name, Phone, Email, Membership Type
3. اضغط على **Add Customer**
4. سيتم إنشاء Customer ID تلقائياً (مثل: C1001)

### 2️⃣ البحث عن عميل
1. في قسم **Customers**
2. أدخل رقم العميل في حقل "Customer ID"
3. اضغط **Search**
4. ستظهر معلومات العميل ونقاط الولاء

### 3️⃣ حذف عميل
1. ابحث عن العميل أولاً
2. اضغط **Delete Customer**
3. أكد عملية الحذف

### 4️⃣ إنشاء حجز جديد
1. اذهب إلى قسم **Reservations**
2. أدخل Customer ID (يجب أن يكون موجوداً في النظام)
3. حدد رقم الطاولة (مثل: T1, T2, T3) `الطاولات المسبق حجزها لا يمكن حاجزها للمرة الثانبة حتي بتم الغاء الحجز او مرور يوم كامل`
4. أدخل عدد الضيوف
5. اضغط **Make Reservation**
6. سيتم إضافة نقاط ولاء تلقائياً (عدد الضيوف × 10)

### 5️⃣ إنشاء تقرير
1. اذهب إلى قسم **Reports**
2. اختر نوع التقرير (Customers / Reservations / Summary)
3. اختر طريقة الترتيب
4. اضغط **Print** للطباعة أو **Save to File** للحفظ

### 6️⃣ تبديل الوضع الداكن
- اضغط زر **Toggle Dark Mode** في أعلى اليمين
- سيتم تبديل الألوان فوراً

---

## التقنيات المستخدمة | Technologies Used

### 🔧 هياكل البيانات | Data Structures
- **Binary Search Tree**: لتخزين العملاء بكفاءة
- **ArrayList**: لتخزين الحجوزات
- **ArrayList**: للإحصائيات

### 📊 الخوارزميات | Algorithms
- **Merge Sort**: O(n log n) - ترتيب العملاء والحجوزات
- **Binary Search**: O(log n) - البحث في الشجرة
- **Tree Traversal**: O(n) - عرض البيانات المرتبة

### 🖼️ الواجهة الرسومية | GUI Components
- **Swing**: المكتبة الرئيسية
- **JFrame**: النافذة الرئيسية
- **JPanel**: لوحات المحتوى
- **Graphics2D**: للرسم المتقدم
- **BorderLayout, BoxLayout**: لتنظيم العناصر

### 🎨 Design Patterns
- **MVC (Model-View-Controller)**:
  - Model: Customer, Reservation
  - View: RestaurantView
  - Controller: RestaurantController
- **Observer Pattern**: لتحديث الألوان

---

## المميزات التقنية | Technical Features

### ✅ التحقق من البيانات | Data Validation
```java
// التحقق من وجود العميل قبل الحجز
if (searchCustomer(customerID) == null) {
    return null; // فشل في إنشاء الحجز
}
```

### ✅ معالجة الأخطاء | Error Handling
- رسائل خطأ واضحة للمستخدم
- عدم السماح بإدخالات فارغة
- التحقق من صحة رقم العميل

### ✅ الأداء | Performance
- **إضافة عميل**: O(log n)
- **بحث**: O(log n)
- **حذف**: O(log n)
- **ترتيب**: O(n log n)

---

## الأمثلة | Examples

### مثال 1: إضافة عميل
```java
Customer customer = new Customer(
    "C1001",                // رقم العميل
    "Ahmed Hassan",         // الاسم
    "0123456789",          // الهاتف
    "ahmed@email.com",     // البريد
    "Gold"                 // نوع العضوية
);
controller.addNewCustomer(customer);
```

### مثال 2: إنشاء حجز
```java
Reservation reservation = new Reservation(
    "R5001",               // رقم الحجز
    "C1001",               // رقم العميل
    "T5",                  // رقم الطاولة
    new Date(),            // التاريخ
    4                      // عدد الضيوف (4 × 10 = 40 نقطة ولاء)
);
controller.makeNewReservation(reservation);
```

---

## الأخطاء الشائعة وحلها | Common Issues

### ❌ المشكلة: الحجز لا يُنشأ
**السبب**: رقم العميل غير موجود
**الحل**: أضف العميل أولاً قبل إنشاء الحجز

### ❌ المشكلة: لا يمكن الحذف
**السبب**: قد يكون هناك حجوزات مرتبطة
**الحل**: احذف الحجوزات أولاً ثم احذف العميل

### ❌ المشكلة: التقرير فارغ
**السبب**: لا توجد بيانات في النظام
**الحل**: أضف عملاء وحجوزات أولاً

---

## ملاحظات مهمة | Important Notes

1. **أرقام العملاء والحجوزات يجب أن تكون فريدة**
2. **لا يمكن إنشاء حجز بدون عميل مسجل**
3. **الترتيب يستخدم Merge Sort فقط** (أسرع وأكثر استقراراً)
4. **البيانات تُحفظ في الذاكرة فقط** (تُفقد عند إغلاق البرنامج)

---

## الملخص | Summary

### 📊 إحصائيات المشروع
- **عدد الملفات**: 7 ملفات Java
- **إجمالي السطور**: ~1,500 سطر
- **المكتبات**: Java Swing فقط
- **التعقيد الزمني**: O(log n) للعمليات الأساسية

### 🎯 الأهداف المحققة
✅ نظام إدارة مطعم كامل  
✅ واجهة رسومية احترافية  
✅ استخدام Binary Search Tree  
✅ استخدام Merge Sort  
✅ البحث والحذف والإضافة  
✅ تقارير قابلة للطباعة  
✅ وضع داكن/فاتح  
✅ التحقق من البيانات  

### 🏆 النتيجة النهائية
نظام متكامل وسهل الاستخدام يمكن استخدامه في مطعم حقيقي بعد إضافة قاعدة بيانات دائمة.

---
لو جيت تشغل المشروع ومرضاش يشتغل علي vscode 
اعمل فولدر و سميه 
## .vscode
و ضيف جواه الفايلين دول

1-launch.json
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Main",
            "request": "launch",
            "mainClass": "Main",
            "projectName": "Java Project_e729be55"
        },
        {
            "type": "java",
            "name": "Run Restaurant Management System",
            "request": "launch",
            "mainClass": "Main",
            "projectName": "Java Project",
            "classPaths": [
                "${workspaceFolder}",
                "${workspaceFolder}/json.jar"
            ]
        }
    ]
}
```

2-settings.json

```json
{
    "java.project.sourcePaths": [
        ""
    ],
    "java.project.referencedLibraries": [
        "json.jar"
    ]
}
```

يعني فالاخر هيبقي عندك :
```
.vscode
   |_ launch.json
   |_ settings.json
```
   

**تم بناء هذا المشروع بواسطة Levi Ackerman باستخدام Java و Swing**

*Last Updated: December 2, 2025*




# شرح الداتابيز والتغييرات - نظام إدارة المطعم 🍽️

## إيه اللي حصل؟ 🤔

### قبل كده:
- البرنامج كان بيشتغل **بس في الذاكرة** (RAM)
- لو قفلت البرنامج → **البيانات تضيع كلها** ❌
- كل مرة تفتح البرنامج تبدأ من الصفر

### دلوقتي:
- البرنامج متوصل بـ **قاعدة بيانات Supabase** (PostgreSQL)
- كل حاجة **بتتحفظ تلقائي** في الداتابيز ✅
- تقفل وتفتح البرنامج تاني → **البيانات موجودة** 🎉
- تقدر تشوف البيانات من أي مكان (Dashboard بتاع Supabase)

---

## الداتابيز بتاعتنا فيها إيه؟ 📊

### جدول العملاء (`customers`)

| العمود | النوع | الشرح |
|--------|------|-------|
| `customer_id` | نص (50 حرف) | رقم العميل (مثلاً: C1001، C1002) |
| `name` | نص (255 حرف) | اسم العميل |
| `phone` | نص (50 حرف) | رقم الموبايل |
| `email` | نص (255 حرف) | الإيميل |
| `membership_type` | نص (50 حرف) | نوع العضوية (Standard, Silver, Gold, Platinum) |
| `created_at` | تاريخ ووقت | امتى اتعمل السجل ده |

### جدول الحجوزات (`reservations`)

| العمود | النوع | الشرح |
|--------|------|-------|
| `reservation_id` | نص (50 حرف) | رقم الحجز (مثلاً: R5001، R5002) |
| `customer_id` | نص (50 حرف) | رقم العميل (مربوط بجدول العملاء) |
| `table_number` | نص (50 حرف) | رقم الترابيزة (مثلاً: T1، T2) |
| `reservation_date` | تاريخ ووقت | امتى الحجز |
| `number_of_guests` | رقم صحيح | عدد الضيوف |
| `status` | نص (50 حرف) | حالة الحجز (Confirmed, Cancelled, Completed) |
| `created_at` | تاريخ ووقت | امتى اتعمل الحجز |

---

## إزاي الموضوع بيشتغل؟ ⚙️

### 1️⃣ لما تفتح البرنامج:
```
البرنامج يفتح
    ↓
يتصل بالداتابيز
    ↓
يجيب كل العملاء والحجوزات
    ↓
يحطهم في الذاكرة (BST و ArrayList)
    ↓
يعرضهم في الواجهة
```

**رسالة في الكونسول:**
```
✅ Loaded: 5 customers, 12 reservations
```

---

### 2️⃣ لما تضيف عميل جديد:
```
تكتب البيانات (اسم، موبايل، إيميل، نوع العضوية)
    ↓
تدوس "Add Customer"
    ↓
يتحفظ في الذاكرة (BST)
    ↓
يتحفظ في الداتابيز (Supabase) 💾
    ↓
يظهرلك رسالة نجاح
```

**لو في مشكلة:**
```
❌ Failed to save customer to database: [سبب الخطأ]
```
بس العميل **هيفضل في الذاكرة** لحد ما تقفل البرنامج

---

### 3️⃣ لما تعمل حجز:
```
تكتب (رقم العميل، رقم الترابيزة، عدد الضيوف)
    ↓
البرنامج يتأكد ان العميل موجود
    ↓
يتأكد ان الترابيزة فاضية
    ↓
يعمل الحجز ويحفظه في الذاكرة
    ↓
يحفظه في الداتابيز 💾
    ↓
يحدث نقاط العميل في الداتابيز 💾
```



### 4️⃣ لما تلغي حجز:
```
تكتب رقم الحجز
    ↓
البرنامج يجيب تفاصيل الحجز
    ↓
تأكد الإلغاء
    ↓
الحالة تتغير لـ "Cancelled"
    ↓
يتحفظ في الداتابيز 💾
    ↓
الترابيزة ترجع فاضية تاني ✅
```

---

### 5️⃣ لما تمسح عميل:
```
تكتب رقم العميل
    ↓
تأكد المسح
    ↓
يتمسح من الذاكرة (BST)
    ↓
يتمسح من الداتابيز 💾
    ↓
كل حجوزاته تتمسح معاه تلقائي (Cascade Delete)
```

---

## الملفات الجديدة اللي اتضافت 📁

### 1. `SupabaseClient.java` (271 سطر)
**ده المسؤول عن الكلام مع الداتابيز**

**بيعمل إيه؟**
- بيتصل بـ Supabase
- بيبعت طلبات HTTP (GET, POST, PATCH, DELETE)
- بيحول البيانات من JSON لـ Java Objects
- بيتعامل مع التواريخ بصيغة ISO 8601

**الدوال المهمة:**
```java
getAllCustomers()      // يجيب كل العملاء
getCustomerById()      // يجيب عميل معين
insertCustomer()       // يضيف عميل جديد
updateCustomer()       // يحدث بيانات عميل
deleteCustomer()       // يمسح عميل

getAllReservations()   // يجيب كل الحجوزات
insertReservation()    // يضيف حجز جديد
updateReservation()    // يحدث حجز (مثلاً: إلغاء)
deleteReservation()    // يمسح حجز
```

---

### 2. `database_setup.sql`
**الكود اللي بتشغله في Supabase عشان يعمل الجداول**

```sql
-- جدول العملاء
CREATE TABLE customers (
    customer_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    email VARCHAR(255),
    membership_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW()
);

-- جدول الحجوزات
CREATE TABLE reservations (
    reservation_id VARCHAR(50) PRIMARY KEY,
    customer_id VARCHAR(50) FOREIGN KEY,
    -- باقي الأعمدة...
);
```

---

### 3. `json.jar`
**مكتبة عشان نقرأ ونكتب JSON**

- حجمها: 74 KB
- بتساعدنا نتعامل مع الـ REST API
- لازم تكون في المجلد عشان البرنامج يشتغل

---

## التعديلات على الملفات القديمة 🔧

### `RestaurantController.java`
**قبل:**
```java
public RestaurantController() {
    this.customers = new BinarySearchTree<>();
    this.reservations = new ArrayList<>();
}
```

**بعد:**
```java
public RestaurantController() {
    this.customers = new BinarySearchTree<>();
    this.reservations = new ArrayList<>();
    this.supabase = new SupabaseClient();    // ← جديد
    
    if (useDatabase) {                        // ← جديد
        loadDataFromDatabase();               // ← جديد
    }
}
```

**كل دالة بقت بتحفظ في الداتابيز:**
```java
public String addNewCustomer(...) {
    // ... الكود العادي
    
    if (useDatabase) {                // ← جديد
        try {
            supabase.insertCustomer(customer);
        } catch (Exception e) {
            System.err.println("❌ فشل الحفظ!");
        }
    }
    
    return id;
}
```

---

## إزاي تشغل البرنامج دلوقتي؟ 🚀

### الطريقة 1: من VSCode (موصى بها)
```
اضغط F5
أو
اضغط ▶️ Run (فوق في اليمين)
```

### الطريقة 2: من Terminal
```powershell
# تجميع
javac -cp ".;json.jar" *.java

# تشغيل
java -cp ".;json.jar" Main
```

**ملاحظة مهمة:** 
- لازم تكتب `-cp ".;json.jar"` عشان المكتبة
- لو نسيت، البرنامج مش هيلاقي `JSONArray` و `JSONObject`

---

## إزاي تعمل Setup للداتابيز؟ 🛠️

### خطوة 1: افتح Supabase Dashboard
```
1. اذهب لـ: https://www.supabase.com
2. لوجن بالحساب بتاعك
3. اختار المشروع
```


### خطوة 2: تأكد من الجداول
```
1. من القائمة الجانبية → اضغط "Table Editor"
2. المفروض تشوف:
   - customers ✅
   - reservations ✅
```

---

## فايدة الداتابيز إيه؟ 💎

### ✅ المميزات:
1. **البيانات محفوظة دايماً** - مش هتضيع أبداً
2. **Multi-User Ready** - ممكن أكتر من شخص يستخدم النظام
3. **Backup تلقائي** - Supabase بيعمل backup تلقائي
4. **سرعة** - الداتابيز بتستخدم Indexes
5. **أمان** - البيانات مشفرة على السيرفر
6. **Cloud** - تقدر توصل للبيانات من أي مكان

### 📊 الإحصائيات:
- **قبل:** 0 MB (كل حاجة في الرام)
- **بعد:** البيانات محفوظة في PostgreSQL
- **السرعة:** نفس السرعة (عشان في Cache محلي)
- **الأمان:** 🔒 HTTPS + API Key

---

## الخلاصة 🎯

### قبل:
```
Java Application (RAM فقط)
```

### بعد:
```
Java Application ←→ SupabaseClient ←→ Supabase (PostgreSQL)
       ↑                                           ↓
    BST Cache                              Cloud Database
```

### الفرق:
- ✅ البيانات **محفوظة** حتى بعد إغلاق البرنامج
- ✅ الحجوزات والعملاء **متزامنة** مع السحابة
- ✅ جاهز للـ **Production** بعد شوية تحسينات

---


**تم بحب ❤️ يوم 2 ديسمبر 2025**

*البرنامج دلوقتي جاهز للاستخدام الحقيقي!* 🎉

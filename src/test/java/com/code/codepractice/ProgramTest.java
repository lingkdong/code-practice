package com.code.codepractice;

import com.alibaba.fastjson.JSONObject;
import com.code.codepractice.dto.Emplyee;
import com.code.codepractice.dto.Person;
import com.code.codepractice.dto.Student;
import com.google.common.base.Splitter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @Author: dongxin
 * @Date: 2019/2/25 10:41
 **/
public class ProgramTest {
    @Test
    public void test() {
        System.out.println(fibonacci(7));
        System.out.println(ratio(7));
    }
/***************************fibonacicc number***************************/
    /**
     * fibonacci number
     * 斐波那切数列
     * 1,2,3,5,8,13,21
     */
    public Long fibonacci(int n) {
        if (n <= 2) return Long.valueOf(n);
        Long first = 1L, second = 2L, result = 0L;

        for (int i = 2; i < n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }
    /**
     * 跳阶(1,2) 也是fibonacii number
     * step=1 or 2
     * n个台阶有几种跳法
     * 列出前几项 递推
     * 台阶数                                           跳法
     * 1                                                  1
     * 2                                                  2
     * 3 (先1后面2个台阶 2 种跳，先2后面1个台阶有 1种跳)  3
     * 4 (先1后面3个台阶 3 种跳，先2后面2个台阶有 2种跳)  5
     * 5 (先1后面4个台阶 5 种跳，先2后面3个台阶有 3种跳)  8
     * 6 (先1后面5个台阶 8 种跳，先2后面4个台阶有 5种跳)  13
     * 7 (先1后面6个台阶 13 种跳，先2后面5个台阶有 8种跳) 21
     */

    /***************************fibonacicc number***************************/


    /***************************ratio number***************************/

    /**
     * 跳阶(1,2,3,4 ...n)
     * step 1 or 2 or 3 or 4 ...or n;
     * n个台阶有几种跳法
     * 递推
     * 台阶数                                                                               跳法
     * 1                                                                                           1
     * 2                                                                                           2
     * 3 (先1后2，2种；先2后1,1种；先3，1种) 2+1+1                                                 4
     * 4 (先1后3，4种；先2后2,2种；先3后1，1种，先4 1种) 4+2+1+1                                   8
     * 5 (先1后4，8种；先2后3,4种；先2后2，2种，先4后1，1种；先5 1种) 8+4+2+1+1                    16
     * 6 (先1后5，16种；先2后4,8种；先3后3，4种，先4后2，2种；先5后1 1种 先6 1种) 16+8+4+2+1+1     32
     * 2的(n-1)次方
     * 等比例数列
     */
    public Double ratio(int n) {
        return Math.pow(2, n - 1);
    }

    /***************************ratio number***************************/


    @Test
    public void testReplace() {
        System.out.println(Math.pow(2, 8));
        System.out.println(Math.log(65536) / Math.log(2));
        System.out.println(Math.pow(2, 16));
    }


    @Test
    public void testSwitch() {
        sw(1);
        sw(2);
        sw(5);
    }

    private void sw(int value) {
        switch (value) {
            case 1:
                System.out.println(value);
                break;
            case 2:
            case 3:
            case 4:
                System.out.println("not 1 value=" + value);
                break;
            case 5:
                System.out.println(value);
                break;
        }
    }


    @Test
    public void testStringFormat() {
        System.out.println(String.format("this is test %s,%s", "format", 1));
    }

    @Test
    public void testJson() {
        List<Top> tops = Arrays.asList(
                Top.newTop()
                        .amount(479.99D)
                        .orderNo("8180723100966900")
                        .build(), Top.newTop()
                        .amount(779.99D)
                        .orderNo("8180723100966902")
                        .build(), Top.newTop()
                        .amount(79.99D)
                        .orderNo("8180723100966903")
                        .build(), Top.newTop()
                        .amount(109.99D)
                        .orderNo("8180723100966904")
                        .build()
        );

        tops.stream().forEach(item -> System.out.println(item.getOrderNo()));
      /*  //打乱顺序
        tops.parallelStream().forEach(item->{
            System.out.println(item.getOrderNo());
        });*/

        tops = tops.parallelStream().sorted(Comparator.comparing(Top::getAmount).reversed()).collect(Collectors.toList());

        tops.stream().forEach(item -> System.out.println(item.getAmount()));
    }

    //overlap
    @Test
    public void testList() {
        List<Top> test = Arrays.asList(
                Top.newTop().orderNo("商品销量02").amount(10d).build(),
                Top.newTop().orderNo("助手测试爽肤水绿茶蜂蜜茉莉味2").amount(100d).build(),
                Top.newTop().orderNo("测试测试经销商来源品牌1测试测试测试").amount(100d).build(),
                Top.newTop().orderNo("满减活动商品").amount(100d).build()
        );

        List<Top> test2 = test.stream()
                .sorted(Comparator.comparing(Top::getOrderNo))
                .collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(test));
        System.out.println(JSONObject.toJSONString(test2));
        Collections.sort(test, new Comparator<Top>() {
            Collator collator = Collator.getInstance(java.util.Locale.CHINA);

            @Override
            public int compare(Top o1, Top o2) {
                return collator.compare(o1.getOrderNo(), o2.getOrderNo());
            }
        });
        System.out.println(JSONObject.toJSONString(test));
        System.out.println(JSONObject.toJSONString(test));
    }

    @Test
    public void testGuava() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("test-%d").build();
    }

    @Test
    public void testDouble() {
        List<Double> doubles = Arrays.asList(0.2D, 300.03D);
        Double total = 0D;
        BigDecimal total2 = BigDecimal.ZERO;
        BigDecimal total3 = BigDecimal.ZERO;
        for (int i = 0; i < doubles.size(); i++) {
            Double item = doubles.get(i);
            total = total + item;
            total2 = total2.add(new BigDecimal(item));
            total3 = total3.add(new BigDecimal(item.toString()));
        }
        System.out.println(total);
        System.out.println(total2);
    }


    @Test
    public void testArray() throws InstantiationException, IllegalAccessException {
        Student student = new Student("benjieming", "本");
        Person[] arr = new Person[]{
                student,
                new Emplyee("ceo", "mark")
        };
//        printPerson(arr);
        List<Student> students = new ArrayList<>();
        students.add(new Student("aa", "Sort test"));
        students.add(student);
//       Student student2=buildObject(Student.class);
//        students.add(student2);
//        printPerson(students);

        students.sort(Comparator.comparing(Student::getCode).reversed());
        printPerson(students);

        List<Long> longs = Arrays.asList(1L, 9L, 7L);
        longs.sort(Comparator.comparingLong(Long::longValue).reversed());
        System.out.println(longs);
    }

    private void printPerson(Person... arr) {
        Arrays.stream(arr).forEach(item -> {
            System.out.println(item.toString());
        });
    }

    private void printPerson(Collection<? extends Person> persons) {
        persons.stream().forEach(item -> {
            System.out.println(item.toString());
        });
    }

    private static <T> T buildObject(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }

    @Test
    public void TestInstanceOf() {
        List<String> test = Arrays.asList("1", "2");
        System.out.println(test);
        Object obj = test;
        List<Integer> integers = (List<Integer>) obj;
        System.out.println(integers);//正常输出 为做转换
        integers.stream().forEach(item -> System.out.println(item));//此处报错
    }

    @Test
    public void TestArray() {
        String[] arr = new String[2];//arr 已是 String 类型数组
        Object[] objs = arr;//String array 指向 objs
        arr[0] = "test";
        objs[1] = Long.valueOf(1);//此处报错
        System.out.println(objs);
    }

    private static String formatFixedLengthSeq(int size, long seq) {
        int length = String.valueOf(seq).length();
        int subSeq;
        if (length >= size) {
            subSeq = Integer.parseInt(String.valueOf(seq).substring(length - size, length));
        } else {
            subSeq = Integer.parseInt(String.valueOf(seq));
        }
        return String.format("%0" + size + "d", subSeq);
    }

    @Test
    public void testDuplicate() {
        DateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");
        Random random = new Random();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            String item = df.format(new Date()) + formatFixedLengthSeq(7, random.nextInt(1000000));
            if (list.contains(item)) {
                System.out.println(String.format("duplicate data=%s,i=%s", item, i));
                System.exit(0);
            } else {
                list.add(item);
            }
        }
    }

    @Test
    public void testProperty() {

    }

    public void readProperty(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                o = fields[i].get(obj);
                System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDefault(){
         List<Person> list=Arrays.asList(new Person("1","1"),new Person("2","2"));
         list.forEach(item->item.setCode("haha"));
        System.out.println(JSONObject.toJSONString(list));
    }
    @Test
    public void TestHiddenIterator(){
        HiddenIterator hiddenIterator=new HiddenIterator();
        Runnable runnable= () -> {
            for(int i=0;i<1000;i++){
                hiddenIterator.addTenThings();

            }
        };
        Runnable runnable3= () -> {
            for(int i=0;i<1000;i++){
              hiddenIterator.addTenThings();
            }
        };
        runnable.run();
        runnable3.run();

    }

    @Test
    public void getCpus(){
        System.out.println( Runtime.getRuntime().availableProcessors());
        System.out.println(Top.class.getAnnotations());

    }

    @Test
    public void testThreadPool(){
        ExecutorService executorService= Executors.newCachedThreadPool();
        if(executorService instanceof ThreadPoolExecutor){
            ((ThreadPoolExecutor)executorService).setCorePoolSize(10);
        }
    }

    @Test
    public void testClassLoader() throws Exception {
        Top student=new Top();
        System.out.println(student.getClass().getClassLoader());//application classLoader
        System.out.println(student.getClass().getClassLoader().getParent());//Extension classLoader
        System.out.println(student.getClass().getClassLoader().getParent().getParent());
        //-Xmn1m -Xms:1m  -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError//成dump文件
        System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024));
        System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024));
        String test=new String();
        while (true){
            //自我复制 快速失败
            test+=test+"this is test OutOfMemoryError.";
        }



    }

    /**
     * 权重算法
     */
    @Test
    public void weight(){
        Map map=new HashMap();
        map.put("127.0.0.1:80",10);//[0,10)
        map.put("127.0.0.1:81",20);//[10,30)
        map.put("127.0.0.1:86",35);//[30,65)
        map.put("127.0.0.1:87",40);//[65,105)

        //权重排序
        List<Map.Entry<String,Integer>> arrayList=new ArrayList<>(map.entrySet());
        arrayList.sort(Comparator.comparingInt(Map.Entry::getValue));

        Map<String,Integer[]> rangMap=new HashMap();
        final Integer[] totalWeight = {0};
        arrayList.stream().forEach(item->{
            rangMap.put(item.getKey(),new Integer[]{totalWeight[0], totalWeight[0] +=item.getValue()});
            System.out.println(totalWeight[0]);
        });

        for(int i=0;i<100;i++){
            getWeight(totalWeight[0]-1,rangMap);
        }
    }

    private void  getWeight(Integer totalWeight,Map<String,Integer[]> rangMap){
        Integer randomV=RandomUtils.nextInt(0,totalWeight);

        for(Map.Entry<String,Integer[]> item:rangMap.entrySet()){
            if(randomV>=item.getValue()[0] && randomV<item.getValue()[1]){
                System.out.println("randmValue="+randomV+",ip="+item.getKey());
                return;
            }
        }
        System.out.println(" no found");
    }

    @Test
    public void testBetween() throws UnsupportedEncodingException {
        String test="a1,b2,cc3";
      /*  System.out.println(StringUtils.substringBetween(test,"a","b"));
        System.out.println(StringUtils.substringBetween(test,"b","c"));
        System.out.println(StringUtils.substringAfter(test,"c"));
        System.out.println(StringUtils.substringAfterLast(test,"c"));


        String dateStr = "20201112131106";
        System.out.println(dateStr);
        System.out.println(dateStr.substring(0,8));
        System.out.println(dateStr.substring(8));*/


        System.out.println(StringUtils.left(test,4000));
        System.out.println(StringUtils.left(null,4000));

        List str= Splitter.on("thmx").splitToList("2|thmx|m|11");
        System.out.println(str.size());

    }
}

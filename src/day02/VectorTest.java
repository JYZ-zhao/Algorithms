package day02;

import java.util.Enumeration;
import java.util.Vector;

public class VectorTest {


        //示例代码
        public static void main(String[] args) {
            Vector vec = new Vector();
//添加
            vec.add("1");
            vec.add("2");
            vec.add("3");
            vec.add("4");
            vec.add("5");
//替换
            vec.set(0, "100");
            vec.add(2, "300");

            System.out.println("vec:"+vec);
            System.out.println("vec.indexOf(100):"+vec.indexOf("100"));
            System.out.println("vec.lastIndexOf(100):"+vec.lastIndexOf("100"));
            System.out.println("vec.firstElement():"+vec.firstElement());
            System.out.println("vec.elementAt(2):"+vec.elementAt(2));
            System.out.println("vec.lastElement():"+vec.lastElement());
            System.out.println("size:"+vec.size());
            System.out.println("capacity:"+vec.capacity());
            System.out.println("vec 2 to 4:"+vec.subList(1, 4));
            Enumeration enu = vec.elements();
            while(enu.hasMoreElements())
            {
                System.out.println("nextElement():"+enu.nextElement());
                Vector retainVec = new Vector();
                retainVec.add("100");
                retainVec.add("300");
                System.out.println("vec.retain():"+vec.retainAll(retainVec));
                System.out.println("vec:"+vec);
                String[] arr = (String[]) vec.toArray(new String[0]);
                for (String str:arr)
                    System.out.println("str:"+str);
                vec.clear();
                vec.removeAllElements();
                System.out.println("vec.isEmpty():"+vec.isEmpty());
            }
        }



    }

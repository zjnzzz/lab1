package test;

import java.io.*;
import java.math.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

class G {
  String[] flag;// jiazhushi
  int[][] d;
  int biaoji;
  List<String> list;
  List<Integer> shortpath;

  G(String[] flag, int[][] d, int biaoji, List<String> list, List<Integer> shortpath) {
    this.flag = flag;
    this.d = d;
    this.biaoji = biaoji;
    this.list = list;
    this.shortpath = shortpath;
  }
}

class Coordinate {
  
  public double x, y;
}

class PantingTest extends JFrame {
  public Coordinate[] c;
  public int v;
  String[] flag;
  int[][] d;
  List<String> list;
  List<Integer> shortpath;
  int biaoji;

  PantingTest(int n, String[] flag, int[][] d, List<String> list, int biaoji,
      List<Integer> shortpath) {
    super("Test");
    v = n;
    this.flag = flag;
    this.d = d;
    this.list = list;
    this.biaoji = biaoji;
    this.shortpath = shortpath;
    setVisible(true);
    setSize(500 * v, 500 * v);
    this.getContentPane().setBackground(Color.BLACK);
  }

  void calCoord() {
    c = new Coordinate[v];
    for (int i = 0; i < v; i++) {
      c[i] = new Coordinate();
      c[i].x = Math.cos(i * 2 * Math.PI / v) * 15 * v + 17 * v;
      c[i].y = Math.sin(i * 2 * Math.PI / v) * 15 * v + 17 * v;
    }
  }

  public void paint(Graphics g) {
    super.paint((Graphics2D) g);
    g.setFont(new Font("Consolas", Font.BOLD, 12));
    g.setColor(Color.WHITE);
    calCoord();
    for (int i = 0; i < v; i++) {
      g.drawString(flag[i], (int) c[i].x + 20, (int) c[i].y + 40);
      g.drawOval((int) c[i].x, (int) c[i].y, 80, 80);
    }
    for (int i = 0; i < v; i++) {
      for (int j = 0; j < v; j++) {
        if (d[i][j] > 0) {
          g.setColor(Color.orange);
          g.drawLine((int) c[i].x + 40, (int) c[i].y + 40, (int) c[j].x + 40, (int) c[j].y + 40);
          g.setColor(Color.YELLOW);
          g.drawString(Integer.toString(d[i][j]), ((int) c[i].x + (int) c[j].x + 80) / 2,
              ((int) c[i].y + (int) c[j].y + 80) / 2);
          int x0 = (int) c[i].x + 40;
          int y0 = (int) c[i].y + 40;
          int x1 = (int) c[j].x + 40;
          int y1 = (int) c[j].y + 40;
          double height11 = 15;
          double length11 = 10;
          int x3 = 0;
          int y3 = 0;
          int x4 = 0;
          int y4 = 0;
          double awrad = Math.atan(length11 / height11); // 箭头角度
          double arraowlen11 = Math.sqrt(length11 * length11 + height11 * height11); // 箭头的长度
          double[] arrXY11 = rotateVec(x1 - x0, y1 - y0, awrad, true, arraowlen11);
          double[] arrXY21 = rotateVec(x1 - x0, y1 - y0, -awrad, true, arraowlen11);
          double x311 = x1 - arrXY11[0]; // (x3,y3)是第一端点
          double y311 = y1 - arrXY11[1];
          double x411 = x1 - arrXY21[0]; // (x4,y4)是第二端点
          double y411 = y1 - arrXY21[1];
          Double x3f = new Double(x311);
          x3 = x3f.intValue();
          Double y3f = new Double(y311);
          y3 = y3f.intValue();
          Double x4f = new Double(x411);
          x4 = x4f.intValue();
          Double y4f = new Double(y411);
          y4 = y4f.intValue();
          g.setColor(Color.RED);
          g.drawLine(x1, y1, x4, y4);
          g.drawLine(x1, y1, x3, y3);
        }

      }
    }
    if (biaoji == 1) {
      for (int i = 0; i < shortpath.size() - 1; i++) {
        g.setColor(Color.GREEN);
        int a = shortpath.get(i);
        int b = shortpath.get(i + 1);
        g.drawLine((int) c[a].x + 40, (int) c[a].y + 40, (int) c[b].x + 40, (int) c[b].y + 40);
      }
    }
  }

  public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
    double [] mathstr = new double[2];
    double vx = px * Math.cos(ang) - py * Math.sin(ang);
    double vy = px * Math.sin(ang) + py * Math.cos(ang);
    if (isChLen) {
      double d = Math.sqrt(vx * vx + vy * vy);
      vx = vx / d * newLen;
      vy = vy / d * newLen;
      mathstr[0] = vx;
      mathstr[1] = vy;
    }
    return mathstr;
  }

  private String w;
}

class Chuli {
  final int max = 999999999;
  public int set;
  public int count2;
  public int count;
  public int signal;
  String[] flag;
  int[][] d;
  List<String> list;
  public int[][] d2;
  public int[] path;
  public int[] road;
  List<Integer> shortpath;
  int shortpathlength;
  List<Integer> suiji;

  Chuli(String[] flag, int[][] d, List<String> list) {
    this.flag = flag;
    this.d = d;
    this.list = list;
    this.d2 = new int[this.list.size()][this.list.size()];
    path = new int[this.list.size()];
    for (int i = 0; i < this.list.size(); i++) {
      for (int j = 0; j < this.list.size(); j++) {
        this.d2[i][j] = d[i][j];
      }
    }
    for (int i = 0; i < this.list.size(); i++) {
      for (int j = 0; j < this.list.size(); j++) {
        if ((i != j) && this.d2[i][j] == 0) {
          d2[i][j] = max;
        }
      }
    }
  }

  void showDirectedGraph(G a) {
    PantingTest b = new PantingTest(a.list.size(), a.flag, a.d, a.list, a.biaoji, a.shortpath);
    b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  String[] queryBridgeWords(String word1, String word2) {
    int a = list.indexOf(word1);
    int b = list.indexOf(word2);
    int count = 0;
    String[] shuchu = new String[flag.length];
    for (int i = 0; i < this.list.size(); i++) {
      if ((d[a][i] > 0) && (d[i][b] > 0)) {
        shuchu[count] = flag[i];
        count++;
      }
    }
    this.count = count;
    return shuchu;
  }

  void fuzhuqueryBridgeWords(String word1, String word2) {
    if ((list.contains(word1) && list.contains((word2))) == false) {
      signal = 2;
    } else {
      queryBridgeWords(word1, word2);
      if (count > 0) {
        signal = 1;
      } else {
        signal = 2;
      }
    }

  }

  void initialization() {
    count2 = 0;
    road = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      road[i] = -1;
    }
  }

  String generateNewText(String shuru) {
    String[] shuru2 = shuru.toLowerCase().split("[^a-z]++");
    String out = "";
    for (int i = 0; i < shuru2.length - 1; i++) {
      fuzhuqueryBridgeWords(shuru2[i], shuru2[i + 1]);
      if (signal == 1) {
        String[] shuchu = queryBridgeWords(shuru2[i], shuru2[i + 1]);
        out += shuru2[i] + " " + shuchu[(int) (Math.random() * count)] + " ";
      } else if (signal == 2) {
        out += shuru2[i] + " ";
      }
    }
    out += shuru2[shuru2.length - 1] + "\n";
    return out;
  }

  String calcShortestPath(String word1, String word2) {
    int[] distance = new int[list.size()];
    int[] visit = new int[list.size()];
    int min;
    int f = 0;
    int a = list.indexOf(word1);
    int b = list.indexOf(word2);
    String listshortpath = "";
    for (int i = 0; i < list.size(); i++) {
      distance[i] = d2[a][i];
      if (distance[i] == max) {
        path[i] = -1;
      } else {
        path[i] = a;
      }
    }
    visit[a] = 1;
    for (int i = 0; i < list.size() - 1; i++) {
      min = max;
      for (int j = 0; j < list.size(); j++) {
        if ((distance[j] <= min) && (visit[j] == 0)) {
          min = distance[j];
          f = j;
        }
      }
      visit[f] = 1;
      for (int k = 0; k < list.size(); k++) {
        if ((distance[k] > distance[f] + d2[f][k]) && (visit[k] == 0)) {
          distance[k] = distance[f] + d2[f][k];
          this.path[k] = f;
        }
      }
    }
    if (distance[b] == max) {
      shortpathlength = max;
    } else {
      shortpathlength = distance[b];
      listshortpath += String.valueOf(distance[b]) + " ";
      findpath(a, b);
      for (int i = list.size() - 1; i >= 0; i--) {
        if (road[i] != -1) {
          shortpath.add(road[i]);
          listshortpath += flag[road[i]] + "->";
        }
      }
      shortpath.add(list.indexOf(word2));
      listshortpath += word2;
    }
    return listshortpath;
  }

  void findpath(int a, int b) {
    for (int i = 0; i < list.size(); i++) {
      if (path[b] != a) {
        road[i] = path[b];
        path[b] = path[path[b]];
      } else {
        road[i] = a;
        break;
      }
    }
  }

  boolean chaxun(int a) {
    suiji = new ArrayList<Integer>();
    for (int i = 0; i < list.size(); i++) {
      if (d[a][i] > 0) {
        suiji.add(i);
      }
    }
    if (suiji.size() > 0) {
      return true;
    } else {
      return false;
    }
  }

  String[] randomWalk() {
    List<String> list2 = new ArrayList<String>();
    int a = (int) (Math.random() * list.size());
    chaxun(a);
    while (true) {
      int i = (int) (Math.random() * suiji.size());
      if ((d[a][suiji.get(i)] > 0)
          && (list2.contains((flag[a] + " " + flag[suiji.get(i)])) == false)) {
        list2.add(flag[a] + " " + flag[suiji.get(i)]);
        a = suiji.get(i);
        chaxun(a);
      } else if (list2.contains(flag[a] + " " + flag[suiji.get(i)])) {
        list2.add(flag[a] + " " + flag[suiji.get(i)]);
        break;
      } else if (chaxun(a) == false) {
        list2.add(flag[a]);
        set = 1;
        break;
      }
    }
    String[] shu = new String[list2.size()];
    int i = 0;
    for (String ss : list2) {
      shu[i] = ss;
      i++;
    }
    return shu;
  }
}

public class test {
  public static void main(String[] args) throws Exception {
    Scanner in = new Scanner(System.in);
    String file = in.nextLine();
    BufferedReader input = new BufferedReader(new FileReader(file));
    FileWriter output = new FileWriter(("shuchu.txt"));
    String s = input.readLine();
    String h = "";
    while (h != null) {
      s = s + " " + h;
      h = input.readLine();
    }
    String[] result = s.toLowerCase().split("[^a-z]++");
    for (String aa : result) {
      System.out.println(aa);
    }
    int i = 0;
    String[] flag = new String[result.length];
    int[][] d = new int[result.length][result.length];
    List<String> list = new ArrayList<String>();
    for (String aa : result) {
      if (list.contains(aa) == false) {
        flag[i] = aa;
        i++;
        list.add(aa);
      }
    }
    for (i = 0; i < result.length - 1; i++) {
      d[list.indexOf(result[i])][list.indexOf(result[i + 1])]++;
    }
    Chuli q = new Chuli(flag, d, list);
    int biaoji = 0;
    while (true) {
      System.out.println("Please choose the gongneng:");
      int xuanze = in.nextInt();
      if (xuanze == 0) {
        q.shortpath = new ArrayList<Integer>();
        G a = new G(q.flag, q.d, biaoji, q.list, q.shortpath);
        q.showDirectedGraph(a);
      }
      // Scanner inn = new Scanner(System.in);
      if (xuanze == 1) {
        Scanner inn = new Scanner(System.in);
        String shuru = inn.nextLine();
        String[] shuru2 = new String[2];
        shuru2 = shuru.split(" +");
        if (q.list.contains(shuru2[0]) && q.list.contains((shuru2[1]))) {
          String[] ww = q.queryBridgeWords(shuru2[0], shuru2[1]);
          if (q.count == 0) {
            System.out.println("No bridge words from" + "\"" + shuru2[0] + "\"" + "to" + "\""
                + shuru2[1] + "\"" + "!");
          }
          if (q.count == 1) {
            System.out.println("The bridge words from" + "\"" + shuru2[0] + "\"" + "to" + "\""
                + shuru2[1] + "\"" + "is:" + ww[0] + "!");
          }
          if (q.count > 1) {
            System.out.print("The bridge words from" + "\"" + shuru2[0] + "\"" + "to" + "\""
                + shuru2[1] + "\"" + "is:");
            for (i = 0; i < q.count - 1; i++) {
              System.out.print(ww[i] + ",");
            }
            System.out.print("and" + " " + ww[q.count - 1] + "." + "\n");
          }
        } else {
          if (q.list.contains(shuru2[0]) == false && q.list.contains((shuru2[1])) == false) {
            System.out.println(
                "No" + "\"" + shuru2[0] + "\"" + "and" + "\"" + shuru2[1] + "\"" + "in the graph!");
          } else if (q.list.contains((shuru2[0])) == false && q.list.contains(shuru2[1]) == true) {
            System.out.println("No" + "\"" + shuru2[0] + "\"" + "in the graph!");
          } else {
            System.out.println("No" + "\"" + shuru2[1] + "\"" + "in the graph!");
          }
        }
        inn.close();
      } else if (xuanze == 2) {
        Scanner inn = new Scanner(System.in);
        String shuru = inn.nextLine();
        System.out.println(q.generateNewText(shuru));
        inn.close();
      } else if (xuanze == 3) {
        Scanner inn = new Scanner(System.in);
        String shuru = inn.nextLine() + " ";
        String[] shuru2 = shuru.toLowerCase().split(" +");
        if (shuru2.length == 1) {
          q.shortpath = new ArrayList<Integer>();
          int count = 0;
          for (String ss : q.list) {
            if (ss.equals(shuru2[0]) == false) {
              q.initialization();
              String jieguo = q.calcShortestPath(shuru2[0], ss);
              if (q.shortpathlength == q.max) {
                System.out.println("no path from" + " " + shuru2[0] + " " + "to" + " " + ss);
                count++;
              } else {
                System.out.println(jieguo);
              }
            }
          }
          inn.close();
        } else if (shuru2.length == 2) {
          q.shortpath = new ArrayList<Integer>();
          q.initialization();
          String jieguo = q.calcShortestPath(shuru2[0], shuru2[1]);
          if (q.shortpathlength == q.max) {
            System.out.println("no path from" + " " + shuru2[0] + " " + "to" + " " + shuru2[1]);
          } else {
            biaoji = 1;
            System.out.println(jieguo);
            G a = new G(q.flag, q.d, biaoji, q.list, q.shortpath);
            q.showDirectedGraph(a);
            biaoji = 0;
          }
        }
      } else if (xuanze == 4) {
        q.set = 0;
        String[] jieshou = q.randomWalk();
        int length1 = jieshou.length;
        String[] jieshou2;
        if (q.set == 0) {
          jieshou2 = new String[length1 + 1];
          String[] zanshi;
          for (i = 0; i < length1 - 1; i++) {
            zanshi = jieshou[i].split(" ");
            jieshou2[i] = zanshi[0];
          }
          zanshi = jieshou[length1 - 1].split(" ");
          jieshou2[length1 - 1] = zanshi[0];
          jieshou2[length1] = zanshi[1];

        } else {
          jieshou2 = new String[length1];
          String[] zanshi;
          for (i = 0; i < length1 - 1; i++) {
            zanshi = jieshou[i].split(" ");
            jieshou2[i] = zanshi[0];
          }
          jieshou2[length1 - 1] = jieshou[length1 - 1];
        }
        int length = jieshou2.length;
        int now = 0;
        int lengthnow = length;
        while (true) {
          Scanner stop = new Scanner(System.in);
          int len = stop.nextInt();
          if (lengthnow < len) {
            String a = "";
            for (i = now; i < length; i++) {
              System.out.print(jieshou2[i] + " ");
              a += jieshou2[i] + " ";
            }
            output.write(a + "\n");
            output.flush();
            System.out.println();
            break;
          } else {
            String a = "";
            for (i = now; i < now + len; i++) {
              System.out.print(jieshou2[i] + " ");
              a += jieshou2[i] + " ";
              lengthnow--;
            }
            output.write(a + "\n");
            output.flush();
            now = i;
            System.out.println();
          }
          stop.close();
        }
      }
    }
  }
}
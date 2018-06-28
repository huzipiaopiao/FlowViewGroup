# FlowViewGroup
[ ![Download](https://api.bintray.com/packages/teadoglibrary/flowViewGroup/flowViewGroup/images/download.svg) ](https://bintray.com/teadoglibrary/flowViewGroup/flowViewGroup/_latestVersion)

流式布局，自动换行，测试阶段

#使用方法：
##1、依赖配置
在项目最外面的build.gradle文件中，allprojects节点下的repositories中添加：
```
maven {
            url  "https://dl.bintray.com/teadoglibrary/flowViewGroup"
        }
```
再在app的build.gradle文件中，dependencies节点下添加：

`compile 'com.teaanddogdog:flowviewgroup:1.0.6'`

版本建议根据最新版本修改

##2、代码中使用
在布局xml文件中，把AutoNewLineViewGroup当做父布局，任何它包含的子布局，都会根据宽度自动换行
```
<com.teaanddogdog.flowviewgroup.AutoNewLineViewGroup
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <Button
                android:layout_width="100dp"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="30dp"
                android:text="Button"
                />

            <EditText
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="30dp"
                android:text="EditText"
                />

            <ProgressBar
                android:id="@+id/textView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="30dp"
                android:text="Hello World!3"
                android:textSize="30sp"/>

            <RatingBar
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="30dp"
                android:text="Hello World!5"
                android:textSize="30sp"
                />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignLeft="@+id/textView"
                android:layout_alignStart="@+id/textView"
                android:layout_below="@+id/textView"
                android:layout_marginLeft="20dp"
                android:padding="10dp"
                android:text="TextView"
                android:textSize="30sp"/>


            <RadioButton
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="30dp"
                android:text="RadioButton"
                android:textSize="30sp"
                />

        </com.teaanddogdog.flowviewgroup.AutoNewLineViewGroup>
```
![图片](https://github.com/huzipiaopiao/FlowViewGroup/img/demo_img1.png)
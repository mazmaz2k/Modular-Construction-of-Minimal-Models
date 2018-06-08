'''
Created on Apr 10, 2018
@author: Omri
'''
import os
import io
import random



import plotly.plotly as py

import plotly
import  numpy as np
from scipy import special
import plotly.graph_objs as go
import subprocess

plotly.tools.set_credentials_file(username='mazmaz2k@gmail.com', api_key='XdkN3oaLsZg84nikscFy')
plotly.offline.init_notebook_mode(connected=True)
path = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\bin"
pathToFile = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles\graphTestFiles"
path1 = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles"

def GetJavaOutput(filepath,filename):
    command = "java -classpath "+path+" Rules/MinimalModel " +filepath+"/"+filename
    return os.popen(command).read()



def RandomGraphMaker(M):
    filename = "graph_"+str(M)
    filePath = os.path.join(pathToFile, filename)
    myFile = open(filePath+".txt", 'w', encoding="utf-8")
    line = ""
    for i in range(0, M):
        line = str(i) + " "
        k = random.randint(2, M)
        for j in range(0, k):
            n = random.randint(0, j)
            if n != i:
                line += str(n) + " "
        myFile.write(line + "\n")
    return myFile


filename = "someFile"
#s = GetJavaOutput(pathToFile, filename)
#print(s)
#fmyFile = RandomGraphMaker(13)
#fmyFile.close()

# def parse_to_arrays_and_print_chart(stri):
#     output = stri.split("\n")
#     W_for_x = []
#     w_size = []
#     a_b_size = []
#     seperator_size = []
#     run_time = []
#     print(stri)
#     # print("All the vars : ")
#     print('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++')
#     for idx, val in enumerate(output):
#         if idx == ():
#             continue
#         if idx % 4 == 0:
#             W_for_x.append(val)
#         elif idx % 4 == 1:
#             w_size.append(val)
#         elif idx % 4 == 2:
#             a_b_size.append(val)
#         else:
#             seperator_size.append(val)
#         print(val, end="")
#     print('++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++')
#     # print("\n W for x")
#     # for i in W_for_x:
#     #     print(i, end="")
#     # print("\nw size")
#     # for i in w_size:
#     #     print(i, end="")
#     # print("\nsize of A B ")
#     # for i in a_b_size:
#     #     print(i, end="")
#     # print("\nseperator size ")
#     # for i in seperator_size:
#     #     print(i, end="")
#     return W_for_x, w_size, a_b_size, seperator_size


def dismentle_efficiency(s,a_b_runs):
    seperator_size_array = s.split('separator size: ')
    percentage_of_dismantle_array= s.split('percentage of dismantle: ')
    largest_CC_size_array = s.split('Largest CC: ')
    connected_component_after_dismantle_array = s.split('After dismentle:  ')
    seperator_size = []
    percentage_of_dismantle = []
    largest_CC_size = []
    connected_component_after_dismantle = []
    for idx, val in enumerate(seperator_size_array):
        if idx == 0:
            continue
        seperator_size.append(val.split('\npercentage of dismantle:')[0])
    for idx, val in enumerate(percentage_of_dismantle_array):
        if idx == 0:
            continue
        percentage_of_dismantle.append(val.split(' %')[0])
    for idx, val in enumerate(largest_CC_size_array):
        if idx == 0:
            continue
        largest_CC_size.append(val.split('\nconnected component After dismentle: ')[0])
    for idx, val in enumerate(connected_component_after_dismantle_array):
        if idx == 0:
            continue
        connected_component_after_dismantle.append(val.split(' fin\n')[0].split(''))
    # print("00000000000000000000000000000000000000000000000000000000000000000000000000000000")
    # # for i in connected_component_after_dismantle:
    # #     print(i)
    # print("00000000000000000000000000000000000000000000000000000000000000000000000000000000")

    x = np.arange(1, 6)
    for i, a_b in enumerate(a_b_runs):

        trace = go.Table(
            type='table',

            # values=[['<b>#(A B)</b><br>'],
            #         ['<b>W size</b><br>']],
            # title='Test for dismantle_efficiency when '+str(a_b_runs),
            header=dict(
                values=[['<b>#</b><br>'],
                        ['<b>seperator size</b><br>'],
                        ['<b>Percentage of</b><br><b>dismantle</b>'],
                        ['<b>largest CC size</b><br>'],
                        ['<b>All connected component</b><br><b>after_dismantle</b>']],
                #  ['<b>2</b>'],
                #  ['<b>4</b>'],
                # ['<b>8</b>'],
                #  ['<b>16</b>'],
                #  ['<b>32</b>']] #['W_for_x_array', '# A & B', 'separator_array']
                line=dict(color='rgb(50, 50, 50)'),
                align=['left'] * 5,
                font=dict(color=['rgb(45, 45, 45)'] * 5, size=14),
                fill=dict(color='#d562be'),
            ),
            cells=dict(
                # prefix=[None] * 1 + ['<b>MB'] + ['sec '] + [None] * 3,
                values=[x, seperator_size[i*5:(5*i+5)], percentage_of_dismantle[i*5:(5*i+5)], largest_CC_size[i*5:(5*i+5)],
                        connected_component_after_dismantle[i*5:(5*i+5)]],

            )
        )

        data = [trace]
        plotly.offline.plot(data, filename='testFiles\graphTestFiles\\table_test_for_dismantle_efficiency'+a_b+'.html')


def runtime_table(s):
    runtime_for_lottery_w_array = s.split('Total Run Time for w in mili seconds: ')
    runtime_for_lottery_a_b_array = s.split('Total Run Time for #AB in mili seconds: ')
    runtime_to_create_flow_chart_array = s.split('Total Run Time for create flow Network Graph in mili seconds: ')
    runtime_for_ff_array = s.split('Total Run Time for ff in mili seconds: ')
    runtime_for_contains_array = s.split('Total Run Time for contains in mili seconds: ')
    runtime_for_lottery_w = []
    runtime_for_lottery_a_b = []
    runtime_to_create_flow_chart = []
    runtime_for_ff = []
    runtime_for_contains = []
    a_b_runs_array = s.split('size of #A_B: ')
    a_b_runs = []
    for idx, val in enumerate(a_b_runs_array):
        if idx == 0:
            continue
        a_b_runs.append(val.split(' pp')[0])
    for idx,val in enumerate(runtime_for_lottery_w_array):
        if idx == 0:
            continue
        runtime_for_lottery_w.append(val.split(' milisec')[0])
    for idx,val in enumerate(runtime_for_lottery_a_b_array):
        if idx == 0:
            continue
        runtime_for_lottery_a_b.append(val.split(' milisec')[0])
    for idx, val in enumerate(runtime_to_create_flow_chart_array):
        if idx == 0:
            continue
        runtime_to_create_flow_chart.append(val.split(' milisec')[0])
    for idx, val in enumerate(runtime_for_ff_array):
        if idx == 0:
            continue
        runtime_for_ff.append(val.split(' milisec')[0])
    for idx, val in enumerate(runtime_for_contains_array):
        if idx == 0:
            continue
        runtime_for_contains.append(val.split(' milisec')[0])
    runs =[1, 2, 3, 4, 5,6]
    # for i in run_time:
    #     temp_run.append(str(i + " millisec"))
    # for i in mem_array:
    #     temp_mem.append(str(i + " MB"))
    total = []
    for i in range(0, len(a_b_runs)):
        total.append(str(int(runtime_for_lottery_w[i])+int(runtime_for_lottery_a_b[i])+int(runtime_to_create_flow_chart[i])+
                     int(runtime_for_ff[i]) + int(runtime_for_contains[i])) + " millisec")
    for i in range(0, len(a_b_runs)):
         runtime_for_lottery_w[i] = str(runtime_for_lottery_w[i])+ " millisec"
         runtime_for_lottery_a_b[i] = str(runtime_for_lottery_a_b[i]) + " millisec"
         runtime_to_create_flow_chart[i] = str(runtime_to_create_flow_chart[i]) + " millisec"
         runtime_for_ff[i] = str(runtime_for_ff[i]) + " millisec"
         runtime_for_contains[i] =str(runtime_for_contains[i]) + " millisec"
    trace = go.Table(
        # values=[['<b>#(A B)</b><br>'],
        #         ['<b>W size</b><br>']],
        # title = 'Test for Run Time',
        header=dict(
            values=[['<b>#</b><br>'],
                    ['<b>#AB run</b><br>'],
                    ['<b>Runtime </b><br><b>for lottery w</b>'],
                    ['<b>Runtime for</b><br><b>lottery A an B</b>'],
                    ['<b>Run Time</b><br><b>to create flow chart</b>'],
                    ['<b>Runtime for</b><br><b>Ford Fulkerson </b>'],
                    ['<b>Runtime for</b><br><b>contains </b>'],
                    ['<b>Total runtime </b><br>']],
                     #  ['<b>2</b>'],
                     #  ['<b>4</b>'],
                     # ['<b>8</b>'],
                     #  ['<b>16</b>'],
                     #  ['<b>32</b>']] #['W_for_x_array', '# A & B', 'separator_array']
            line=dict(color='rgb(50, 50, 50)'),
            align=['left'] * 5,
            font=dict(color=['rgb(45, 45, 45)'] * 5, size=14),
            fill=dict(color='#d562be'),
        ),
        cells=dict(
            # prefix=[None] * 1 + ['<b>MB'] + ['sec '] + [None] * 3,
            values=[runs,a_b_runs, runtime_for_lottery_w, runtime_for_lottery_a_b, runtime_to_create_flow_chart,
                    runtime_for_ff,runtime_for_contains,total],

        )
    )

    data = [trace]
    plotly.offline.plot(data, filename='testFiles\graphTestFiles\\table_test_1.html')

def return_avg(arr):
    if len(arr) == 0:
        return 0
    sum_out = 0
    for i in arr:
        sum_out = int(sum_out) + int(float(i))
        # sum_out = 0
    # print(sum_out)
    return sum_out/len(arr)

def get_string_print_chart(s,a_b_runs):
    W_for_x = []
    w_size = []
    a_b_size = []
    seperator_size = []

    for idx, val in enumerate(s):
        if idx == (len(s) - 1):
            break
        if idx % 4 == 0:
            W_for_x.append(val)
        elif idx % 4 == 1:
            w_size.append(val)
        elif idx % 4 == 2:
            a_b_size.append(val)
        else:
            seperator_size.append(val)
    arr = [W_for_x, w_size, a_b_size, seperator_size]
    # average_arr = []
    # for i in arr:
    #     average_arr.append(return_sum(i) / len(i))

    names = ['W_for_x', 'w_size', 'a_b_size', 'seperator_size']
    for x in W_for_x:
        print(x, end=" ")
    print()
    for x in w_size:
        print(x, end=" ")
    print()
    for x in a_b_size:
        print(x, end=" ")
    print()
    for x in seperator_size:
        print(x, end=" ")
    print()
    temp_array =[]
    # temp_array.append(return_avg(seperator_size))
    avg = return_avg(seperator_size)
    for i in range(0, 3):
        #x = [10, 20, 50, 100, 200]
        # if i == 4:
        #     continue
        x = arr[i]
        for x in range(len(arr[i])):
            temp_array.append(avg)
        layout = go.Layout(
            title='TEST for ' + names[i] + ' and '+names[3]+'<br>'+'#A&B is: '+ a_b_runs,
            yaxis=dict(
                title=names[3]
            ),
            xaxis=dict(
                title='' + names[i]
            )
        )
        tracel = go.Scatter(
            x=arr[i],
            y=arr[3],
            mode=' lines + markers',
            name=names[3] + '(#A&B)',
            line=dict(
                shape='spline'
            )
        )
        trace2 = go.Scatter(
            x=arr[i],
            y=temp_array,
            mode='lines + markers',
            name='average',
            line = dict(
                shape='spline'
            )
        )
        fig = go.Figure(data=[tracel, trace2], layout=layout)
        # plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\'+a_b_runs + '__test_separator_and_' + names[i] + '.html')
        # fig2 = go.Figure(data=[trace2], layout=layout2)
        # # plotly.offline.plot(fig2,filename='2.html')


def print_table(mem_array, run_time, min_separator,a_b_runs):
    # mem_array, run_time, min_separator,a_b_runs = create_array()
    # x = [5 , 10 , 20, 40, 80]
    y = [2, 4, 8, 16, 32]
    # res = [[0 for x in range(5)] for y in range(5)]
    # i=int(0)
    # for idx, (x, y) in enumerate(zip(mem_array_usage, run_time_array)):
    #     res[idx][idx](str(x) +' '+ str(y) )
    # res =[]
    # for idx,(x,y) in enumerate(zip(mem_array,run_time)):
    #     if idx != 0:
    #         res.append(str("Mem: "+x+" --- "+"Runtime: " + y))
    # x = [5,10,20,50,100,200]
    temp_mem = []
    temp_run = []
    # for i in range(len(temp_mem)):
    #     temp_mem[i] = str(temp_mem[i] + " MB")
    # for i in range(len(temp_run)):
    #     temp_run[i] = str(temp_run[i] + " millisec")
    for i in run_time:
        temp_run.append(str(i + " millisec"))
    for i in mem_array:
        temp_mem.append(str(i + " MB"))
    trace = go.Table(
        # values=[['<b>#(A B)</b><br>'],
        #         ['<b>W size</b><br>']],
        header=dict(
            values= [['<b>#(A B)</b><br>'],
                     ['<b>Memory Usage</b><br> In MB'],
                     ['<b>Run Time</b><br> In millisec'],
                     ['<b>Min separator size</b>']],
                     # ['<b>2</b>'],
                     #  ['<b>4</b>'],
                     # ['<b>8</b>'],
                     #  ['<b>16</b>'],
                     #  ['<b>32</b>']] #['W_for_x_array', '# A & B', 'separator_array']
            line=dict(color='rgb(50, 50, 50)'),
            align=['left'] * 5,
            font=dict(color=['rgb(45, 45, 45)'] * 5, size=14),
            fill=dict(color='#d562be'),
        ),
        cells=dict(
            # prefix=[None] * 1 + ['<b>MB'] + ['sec '] + [None] * 3,
            values=[a_b_runs, temp_mem, temp_run, min_separator],

        )
    )

    data = [trace]
    plotly.offline.plot(data, filename='testFiles\graphTestFiles\\table.html')


def create_array():
    # output = GetJavaOutput(pathToFile, filename)

    output ='''
Start
16, 15, 0, 0, 16, 15, 1, 0, 16, 15, 2, 0, 16, 15, 3, 0, 16, 15, 4, 0, 32, 27, 1, 0, 32, 27, 2, 0, 32, 27, 3, 0, 32, 27, 4, 0, 
End
Total Run Time for w in mili seconds: 329 milisec
Total Run Time for #AB in mili seconds: 5 milisec
Total Run Time for create flow Network Graph in mili seconds: 0 milisec
Total Run Time for ff in mili seconds: 6754 milisec
Total Run Time for contains in mili seconds: 56 milisec
7144
size of #A_B: 5 pp

Memory usage: 1.5208663940429688 MB
Total Run Time in mili seconds: 7199 milli sec
separator size: 14
percentage of dismantle: 34.0 %
Largest CC: 66
connected component After dismentle: 
66 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 18
percentage of dismantle: 42.00000000000001 %
Largest CC: 58
connected component After dismentle: 
58 3 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 18
percentage of dismantle: 51.0 %
Largest CC: 49
connected component After dismentle: 
49 13 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 19
percentage of dismantle: 64.0 %
Largest CC: 36
connected component After dismentle: 
36 8 3 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 21
percentage of dismantle: 44.99999999999999 %
Largest CC: 55
connected component After dismentle: 
55 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 14 EOF
Start
16, 16, 4, 0, 16, 16, 5, 0, 16, 16, 6, 0, 16, 16, 7, 0, 16, 16, 8, 0, 32, 31, 1, 0, 32, 31, 2, 0, 32, 31, 3, 0, 32, 31, 4, 0, 32, 31, 6, 0, 32, 31, 7, 0, 32, 31, 8, 0, 32, 31, 9, 0, 
End
Total Run Time for w in mili seconds: 247 milisec
Total Run Time for #AB in mili seconds: 37 milisec
Total Run Time for create flow Network Graph in mili seconds: 0 milisec
Total Run Time for ff in mili seconds: 10932 milisec
Total Run Time for contains in mili seconds: 37 milisec
11253
size of #A_B: 10 pp

Memory usage: 1.744537353515625 MB
Total Run Time in mili seconds: 11270 milli sec
separator size: 13
percentage of dismantle: 42.00000000000001 %
Largest CC: 58
connected component After dismentle: 
58 9 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 18
percentage of dismantle: 55.0 %
Largest CC: 45
connected component After dismentle: 
45 5 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 20
percentage of dismantle: 66.0 %
Largest CC: 34
connected component After dismentle: 
34 2 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 20
percentage of dismantle: 69.0 %
Largest CC: 31
connected component After dismentle: 
31 14 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 21
percentage of dismantle: 66.0 %
Largest CC: 34
connected component After dismentle: 
34 7 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 13 EOF
Start
16, 15, 8, 0, 16, 15, 9, 0, 16, 15, 10, 0, 16, 15, 11, 0, 16, 15, 12, 0, 16, 15, 13, 0, 16, 15, 14, 0, 16, 15, 15, 0, 16, 15, 16, 0, 16, 15, 17, 0, 32, 24, 0, 0, 32, 24, 1, 0, 32, 24, 2, 0, 32, 24, 3, 0, 32, 24, 4, 0, 32, 24, 7, 0, 32, 24, 8, 0, 32, 24, 10, 0, 32, 24, 11, 0, 32, 24, 13, 0, 32, 24, 14, 0, 32, 24, 15, 0, 32, 24, 16, 0, 32, 24, 18, 0, 32, 24, 19, 0, 
End
Total Run Time for w in mili seconds: 244 milisec
Total Run Time for #AB in mili seconds: 22 milisec
Total Run Time for create flow Network Graph in mili seconds: 1 milisec
Total Run Time for ff in mili seconds: 25163 milisec
Total Run Time for contains in mili seconds: 55 milisec
25485
size of #A_B: 20 pp

Memory usage: 2.349853515625 MB
Total Run Time in mili seconds: 25528 milli sec
separator size: 15
percentage of dismantle: 57.0 %
Largest CC: 43
connected component After dismentle: 
43 12 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 64.0 %
Largest CC: 36
connected component After dismentle: 
36 17 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 47.0 %
Largest CC: 53
connected component After dismentle: 
53 2 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 62.0 %
Largest CC: 38
connected component After dismentle: 
38 11 3 3 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 38.0 %
Largest CC: 62
connected component After dismentle: 
62 2 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 15 EOF
Start
16, 16, 1, 0, 16, 16, 2, 0, 16, 16, 5, 0, 16, 16, 6, 0, 16, 16, 7, 0, 16, 16, 12, 0, 16, 16, 13, 0, 16, 16, 14, 0, 16, 16, 18, 0, 16, 16, 19, 0, 16, 16, 20, 0, 16, 16, 21, 0, 16, 16, 22, 0, 16, 16, 23, 0, 16, 16, 24, 0, 16, 16, 25, 0, 16, 16, 26, 0, 16, 16, 28, 0, 16, 16, 29, 0, 16, 16, 30, 0, 16, 16, 31, 0, 16, 16, 32, 0, 16, 16, 34, 0, 16, 16, 35, 0, 16, 16, 38, 0, 16, 16, 40, 0, 16, 16, 43, 0, 16, 16, 44, 0, 16, 16, 45, 0, 16, 16, 46, 0, 32, 28, 0, 0, 32, 28, 1, 0, 32, 28, 2, 0, 32, 28, 3, 0, 32, 28, 4, 0, 32, 28, 5, 0, 32, 28, 6, 0, 32, 28, 7, 0, 32, 28, 8, 0, 32, 28, 10, 0, 32, 28, 11, 0, 32, 28, 12, 0, 32, 28, 13, 0, 32, 28, 14, 0, 32, 28, 16, 0, 32, 28, 18, 0, 32, 28, 20, 0, 32, 28, 21, 0, 32, 28, 22, 0, 32, 28, 23, 0, 32, 28, 26, 0, 32, 28, 27, 0, 32, 28, 28, 0, 32, 28, 33, 0, 32, 28, 34, 0, 32, 28, 35, 0, 32, 28, 37, 0, 32, 28, 38, 0, 32, 28, 39, 0, 32, 28, 41, 0, 32, 28, 43, 0, 32, 28, 44, 0, 32, 28, 45, 0, 32, 28, 46, 0, 32, 28, 47, 0, 32, 28, 49, 0, 
End
Total Run Time for w in mili seconds: 95 milisec
Total Run Time for #AB in mili seconds: 108 milisec
Total Run Time for create flow Network Graph in mili seconds: 0 milisec
Total Run Time for ff in mili seconds: 49103 milisec
Total Run Time for contains in mili seconds: 140 milisec
49446
size of #A_B: 50 pp

Memory usage: 4.319000244140625 MB
Total Run Time in mili seconds: 49612 milli sec
separator size: 13
percentage of dismantle: 31.0 %
Largest CC: 69
connected component After dismentle: 
69 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 53.0 %
Largest CC: 47
connected component After dismentle: 
47 5 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 41.0 %
Largest CC: 59
connected component After dismentle: 
59 4 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 17
percentage of dismantle: 42.00000000000001 %
Largest CC: 58
connected component After dismentle: 
58 6 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 18
percentage of dismantle: 43.00000000000001 %
Largest CC: 57
connected component After dismentle: 
57 2 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 13 EOF
Start
16, 16, 3, 0, 16, 16, 4, 0, 16, 16, 5, 0, 16, 16, 6, 0, 16, 16, 7, 0, 16, 16, 8, 0, 16, 16, 10, 0, 16, 16, 11, 0, 16, 16, 13, 0, 16, 16, 14, 0, 16, 16, 15, 0, 16, 16, 19, 0, 16, 16, 20, 0, 16, 16, 21, 0, 16, 16, 22, 0, 16, 16, 23, 0, 16, 16, 24, 0, 16, 16, 30, 0, 16, 16, 34, 0, 16, 16, 36, 0, 16, 16, 39, 0, 16, 16, 41, 0, 16, 16, 42, 0, 16, 16, 43, 0, 16, 16, 44, 0, 16, 16, 45, 0, 16, 16, 47, 0, 16, 16, 48, 0, 16, 16, 49, 0, 16, 16, 50, 0, 16, 16, 51, 0, 16, 16, 55, 0, 16, 16, 58, 0, 16, 16, 61, 0, 16, 16, 62, 0, 16, 16, 63, 0, 16, 16, 64, 0, 16, 16, 66, 0, 16, 16, 67, 0, 16, 16, 68, 0, 16, 16, 71, 0, 16, 16, 72, 0, 16, 16, 73, 0, 16, 16, 74, 0, 16, 16, 75, 0, 16, 16, 77, 0, 16, 16, 79, 0, 16, 16, 80, 0, 16, 16, 81, 0, 16, 16, 82, 0, 16, 16, 85, 0, 16, 16, 87, 0, 16, 16, 89, 0, 16, 16, 90, 0, 16, 16, 93, 0, 16, 16, 94, 0, 16, 16, 96, 0, 16, 16, 97, 0, 16, 16, 99, 0, 32, 23, 0, 0, 32, 23, 1, 0, 32, 23, 2, 0, 32, 23, 3, 0, 32, 23, 5, 0, 32, 23, 6, 0, 32, 23, 7, 0, 32, 23, 8, 0, 32, 23, 9, 0, 32, 23, 10, 0, 32, 23, 11, 0, 32, 23, 12, 0, 32, 23, 13, 0, 32, 23, 14, 0, 32, 23, 16, 0, 32, 23, 17, 0, 32, 23, 19, 0, 32, 23, 20, 0, 32, 23, 21, 0, 32, 23, 22, 0, 32, 23, 23, 0, 32, 23, 25, 0, 32, 23, 26, 0, 32, 23, 28, 0, 32, 23, 35, 0, 32, 23, 36, 0, 32, 23, 39, 0, 32, 23, 40, 0, 32, 23, 41, 0, 32, 23, 42, 0, 32, 23, 43, 0, 32, 23, 45, 0, 32, 23, 46, 0, 32, 23, 47, 0, 32, 23, 48, 0, 32, 23, 49, 0, 32, 23, 50, 0, 32, 23, 53, 0, 32, 23, 54, 0, 32, 23, 55, 0, 32, 23, 57, 0, 32, 23, 59, 0, 32, 23, 60, 0, 32, 23, 61, 0, 32, 23, 62, 0, 32, 23, 65, 0, 32, 23, 66, 0, 32, 23, 67, 0, 32, 23, 68, 0, 32, 23, 70, 0, 32, 23, 72, 0, 32, 23, 73, 0, 32, 23, 74, 0, 32, 23, 76, 0, 32, 23, 77, 0, 32, 23, 79, 0, 32, 23, 80, 0, 32, 23, 82, 0, 32, 23, 83, 0, 32, 23, 84, 0, 32, 23, 85, 0, 32, 23, 86, 0, 32, 23, 87, 0, 32, 23, 90, 0, 32, 23, 93, 0, 32, 23, 94, 0, 32, 23, 95, 0, 32, 23, 99, 0, 
End
Total Run Time for w in mili seconds: 97 milisec
Total Run Time for #AB in mili seconds: 203 milisec
Total Run Time for create flow Network Graph in mili seconds: 0 milisec
Total Run Time for ff in mili seconds: 106924 milisec
Total Run Time for contains in mili seconds: 132 milisec
107356
size of #A_B: 100 pp

Memory usage: 7.3653106689453125 MB
Total Run Time in mili seconds: 107528 milli sec
separator size: 10
percentage of dismantle: 29.0 %
Largest CC: 71
connected component After dismentle: 
71 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 11
percentage of dismantle: 35.0 %
Largest CC: 65
connected component After dismentle: 
65 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 12
percentage of dismantle: 39.0 %
Largest CC: 61
connected component After dismentle: 
61 3 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 13
percentage of dismantle: 37.0 %
Largest CC: 63
connected component After dismentle: 
63 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 14
percentage of dismantle: 39.0 %
Largest CC: 61
connected component After dismentle: 
61 7 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 10 EOF
Start
16, 14, 5, 0, 16, 14, 7, 0, 16, 14, 9, 0, 16, 14, 10, 0, 16, 14, 11, 0, 16, 14, 16, 0, 16, 14, 17, 0, 16, 14, 18, 0, 16, 14, 19, 0, 16, 14, 20, 0, 16, 14, 23, 0, 16, 14, 24, 0, 16, 14, 26, 0, 16, 14, 27, 0, 16, 14, 32, 0, 16, 14, 36, 0, 16, 14, 38, 0, 16, 14, 40, 0, 16, 14, 41, 0, 16, 14, 46, 0, 16, 14, 48, 0, 16, 14, 49, 0, 16, 14, 50, 0, 16, 14, 52, 0, 16, 14, 56, 0, 16, 14, 58, 0, 16, 14, 60, 0, 16, 14, 62, 0, 16, 14, 63, 0, 16, 14, 64, 0, 16, 14, 65, 0, 16, 14, 68, 0, 16, 14, 69, 0, 16, 14, 73, 0, 16, 14, 76, 0, 16, 14, 78, 0, 16, 14, 79, 0, 16, 14, 80, 0, 16, 14, 83, 0, 16, 14, 85, 0, 16, 14, 91, 0, 16, 14, 93, 0, 16, 14, 94, 0, 16, 14, 96, 0, 16, 14, 97, 0, 16, 14, 98, 0, 16, 14, 100, 0, 16, 14, 106, 0, 16, 14, 108, 0, 16, 14, 109, 0, 16, 14, 110, 0, 16, 14, 111, 0, 16, 14, 112, 0, 16, 14, 113, 0, 16, 14, 117, 0, 16, 14, 120, 0, 16, 14, 122, 0, 16, 14, 123, 0, 16, 14, 124, 0, 16, 14, 125, 0, 16, 14, 130, 0, 16, 14, 132, 0, 16, 14, 134, 0, 16, 14, 137, 0, 16, 14, 138, 0, 16, 14, 139, 0, 16, 14, 141, 0, 16, 14, 142, 0, 16, 14, 143, 0, 16, 14, 145, 0, 16, 14, 146, 0, 16, 14, 148, 0, 16, 14, 154, 0, 16, 14, 156, 0, 16, 14, 157, 0, 16, 14, 159, 0, 16, 14, 162, 0, 16, 14, 163, 0, 16, 14, 165, 0, 16, 14, 166, 0, 16, 14, 169, 0, 16, 14, 170, 0, 16, 14, 171, 0, 16, 14, 172, 0, 16, 14, 179, 0, 16, 14, 180, 0, 16, 14, 181, 0, 16, 14, 182, 0, 16, 14, 183, 0, 16, 14, 185, 0, 16, 14, 187, 0, 16, 14, 188, 0, 16, 14, 189, 0, 16, 14, 191, 0, 16, 14, 192, 0, 16, 14, 193, 0, 16, 14, 196, 0, 16, 14, 198, 0, 32, 30, 1, 0, 32, 30, 4, 0, 32, 30, 5, 0, 32, 30, 9, 0, 32, 30, 10, 0, 32, 30, 11, 0, 32, 30, 13, 0, 32, 30, 16, 0, 32, 30, 18, 0, 32, 30, 19, 0, 32, 30, 20, 0, 32, 30, 22, 0, 32, 30, 24, 0, 32, 30, 26, 0, 32, 30, 29, 0, 32, 30, 31, 0, 32, 30, 33, 0, 32, 30, 34, 0, 32, 30, 35, 0, 32, 30, 36, 0, 32, 30, 38, 0, 32, 30, 45, 0, 32, 30, 46, 0, 32, 30, 48, 0, 32, 30, 51, 0, 32, 30, 54, 0, 32, 30, 58, 0, 32, 30, 59, 0, 32, 30, 64, 0, 32, 30, 71, 0, 32, 30, 73, 0, 32, 30, 75, 0, 32, 30, 77, 0, 32, 30, 78, 0, 32, 30, 81, 0, 32, 30, 85, 0, 32, 30, 86, 0, 32, 30, 88, 0, 32, 30, 91, 0, 32, 30, 94, 0, 32, 30, 97, 0, 32, 30, 98, 0, 32, 30, 101, 0, 32, 30, 105, 0, 32, 30, 106, 0, 32, 30, 110, 0, 32, 30, 111, 0, 32, 30, 112, 0, 32, 30, 114, 0, 32, 30, 118, 0, 32, 30, 119, 0, 32, 30, 121, 0, 32, 30, 124, 0, 32, 30, 125, 0, 32, 30, 129, 0, 32, 30, 130, 0, 32, 30, 132, 0, 32, 30, 141, 0, 32, 30, 142, 0, 32, 30, 147, 0, 32, 30, 150, 0, 32, 30, 152, 0, 32, 30, 153, 0, 32, 30, 156, 0, 32, 30, 164, 0, 32, 30, 165, 0, 32, 30, 172, 0, 32, 30, 173, 0, 32, 30, 174, 0, 32, 30, 177, 0, 32, 30, 183, 0, 32, 30, 184, 0, 32, 30, 185, 0, 32, 30, 186, 0, 32, 30, 187, 0, 32, 30, 192, 0, 32, 30, 195, 0, 32, 30, 196, 0, 32, 30, 197, 0, 32, 30, 198, 0, 32, 30, 199, 0, 
End
Total Run Time for w in mili seconds: 92 milisec
Total Run Time for #AB in mili seconds: 146 milisec
Total Run Time for create flow Network Graph in mili seconds: 0 milisec
Total Run Time for ff in mili seconds: 76408 milisec
Total Run Time for contains in mili seconds: 156 milisec
76802
size of #A_B: 200 pp

Memory usage: 9.894073486328125 MB
Total Run Time in mili seconds: 77333 milli sec
separator size: 9
percentage of dismantle: 27.0 %
Largest CC: 73
connected component After dismentle: 
73 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 12
percentage of dismantle: 35.0 %
Largest CC: 65
connected component After dismentle: 
65 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 13
percentage of dismantle: 38.0 %
Largest CC: 62
connected component After dismentle: 
62 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 13
percentage of dismantle: 36.0 %
Largest CC: 64
connected component After dismentle: 
64 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

separator size: 13
percentage of dismantle: 32.0 %
Largest CC: 68
connected component After dismentle: 
68 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 fin

Min seperator size: 9 EOF
'''




    # print(output)
    # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    runtime_table(output)

    a_b_runs_array = output.split('size of #A_B: ')
    a_b_runs =[]

    for idx, val in enumerate(a_b_runs_array):
        if idx == 0:
            continue
        print(val)
        a_b_runs.append(val.split(' pp')[0])
    test = output.split('Start')
    s2 = []
    # s3 = []
    # print("7777777777777777777777777777777777777777")
    for i, val in enumerate(test):
        # print(i)
        # if i != 0:
        #     s3.append(val.split(' pp'))
        s2.append(val.split('End')[0])

    # print("7777777777777777777777777777777777777777")
    dismentle_efficiency(output,a_b_runs)

    # for idx, val in enumerate(s3):
    #     # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    #     # print(val)
    #     # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    #     if idx == 0:
    #         continue
    #     # if(idx<=len(a_b_runs)):
    #     # get_string_print_chart(a, a_b_runs[idx-1])
    #     # for x in a:
    #     #     print(x, end=" ")
    #     print("------------------------------------------------------")
    for idx, val in enumerate(s2):
        # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
        # print(val)
        # print("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
        if idx == 0:
            continue
        # dismentle_efficiency(output, val)
        a = val.split(', ')
        # if(idx<=len(a_b_runs)):
        get_string_print_chart(a, a_b_runs[idx-1])
        # for x in a:
        #     print(x, end=" ")
        a.clear()
        print("------------------------------------------------------")
    mem_array_usage = output.split('Memory usage: ')
    mem_array = []
    run_time_array_usage = output.split('Total Run Time in mili seconds: ')
    # print("tttttttttttttttttttttttt "+mem_array_usage[0])
    min_separator_array = output.split('Min seperator size: ')
    run_time = []
    min_separator= []
    # print("--------------------------------------------------------------------")
    for idx, val in enumerate(mem_array_usage):
        if idx == 0:
            continue
        mem_array.append(val.split(' MB')[0])
        # parse_to_arrays_and_print_chart(i)
    for idx, val in enumerate(run_time_array_usage):
        if idx == 0:
            continue
        run_time.append(val.split(' milli sec')[0])
        # print(i +'\n ^^^^^^^^^^^^^^^^^^^^')
    for idx, val in enumerate(min_separator_array):
        if idx == 0:
            continue
        min_separator.append(val.split(' EOF')[0])

    # for i in mem_array:
    # print(i +'^^^^^^^^^^^^^^^^^^^^^^')
    # print("\n--------------------------------------------------------------------")
    # for i in run_time:
    # print(i +'\n ========================')(
    print_table(mem_array, run_time, min_separator, a_b_runs)
    return mem_array, run_time, min_separator, a_b_runs

#
# def printChart():
#     # W_for_x_array, w_size_array, a_b_array, seperator_array = create_array()
#
#     mem_array_usage, run_time_array = create_array()
#     # x = np.linspace(2, 32)
#     # x= w_size_array
#     x = [10 , 20 , 50, 100, 200]
#     layout = go.Layout(
#         title = 'TEST mem_array_usage',
#         yaxis= dict(
#             title = 'mem usage in MB'
#         ),
#         xaxis= dict(
#             title = '# of A&B'
#         )
#     )
#     tracel = go.Scatter(
#         x = x,
#         y = mem_array_usage,
#         mode = ' lines + markers',
#         name= 'memoryUsage(#A&B)',
#         line = dict(
#             shape = 'spline'
#         )
#     )
#     layout2 = go.Layout(
#         title = 'TEST run_time_array',
#         yaxis= dict(
#             title = 'runtime in mili'
#         ),
#         xaxis= dict(
#             title = ' # of A&B'
#         )
#     )
#     trace2 = go.Scatter(
#         x =x,
#         y = run_time_array,
#         mode = ' lines + markers',
#            name = 'runTime(#A&B)',
#                   line = dict(
#         shape='spline'
#     )
#     )
#     fig = go.Figure(data=[tracel], layout=layout)
#     plotly.offline.plot(fig,filename='testFiles\graphTestFiles\\memUsage.html')
#     fig2 = go.Figure(data=[trace2], layout=layout2)
#     plotly.offline.plot(fig2,filename='testFiles\graphTestFiles\\runtime.html')


def printChart():
    # W_for_x_array, w_size_array, a_b_array, seperator_array = create_array()
    mem_array_usage, run_time_array,min_separator,a_b_runs = create_array()
    # x = np.linspace(2, 32)
    # x= w_size_array
    x = [5,10 , 20 , 50, 100, 200]
    res =[mem_array_usage, run_time_array,min_separator]
    dic = ['mem usage in MB', 'runtime in mili', 'min size of separator' ]
    dic2=['memory Usage', 'run Time', 'separator Size']
    for idx in range(0, 3):
        layout = go.Layout(
            title='TEST for '+dic2[idx],
            yaxis=dict(
                title=dic[idx]
            ),
            xaxis=dict(
                title='#A&B'
            )
        )
        tracel = go.Scatter(
            x=a_b_runs,
            y=res[idx],
            mode=' lines + markers',
            name=dic2[idx]+'(#A&B)',
            line=dict(
                shape='spline'
            )
        )
        fig = go.Figure(data=[tracel], layout=layout)
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\' + dic2[idx] + '.html')



if __name__ == '__main__':
    printChart()
    # print_table()
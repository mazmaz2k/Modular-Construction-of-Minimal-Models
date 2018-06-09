'''
Created on Apr 10, 2018
@author: Omri
'''
import os
import io
import random
from operator import itemgetter

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
    connected_component_after_dismantle_array = s.split('connected component After dismentle: ')
    count_occurence_array=s.split('Min seperator size:')
    count_occurence =[]
    seperator_size = []
    percentage_of_dismantle = []
    largest_CC_size = []
    connected_component_after_dismantle = []
    for idx, val in enumerate(count_occurence_array):
        # if idx == 0:
        #     continue
        count_occurence.append(int(val.count('connected component After dismentle: ')))
    # print("=++___________+++++++++++++++++++++++++++++++++++++++++++++++")
    # for i in count_occurence:
    #     print(i)
    # print("=++___________+++++++++++++++++++++++++++++++++++++++++++++++")
    for idx, val in enumerate(seperator_size_array):
        if idx == 0:
            continue
        seperator_size.append(val.split('\npercentage of dismantle:')[0])
    for idx, val in enumerate(percentage_of_dismantle_array ):
        if idx == 0:
            continue
        percentage_of_dismantle.append(val.split(' %')[0]+" %")
    for idx, val in enumerate(largest_CC_size_array):
        if idx == 0:
            continue
        largest_CC_size.append(val.split('\nconnected component After dismentle: ')[0])
    for idx, val in enumerate(connected_component_after_dismantle_array):
        if idx == 0:
            continue
        # print(val)
        connected_component_after_dismantle.append(val.split(' fin')[0])
    # print("00000000000000000000000000000000000000000000000000000000000000000000000000000000")
    # # for i in connected_component_after_dismantle:
    # #     print(i)
    # print("00000000000000000000000000000000000000000000000000000000000000000000000000000000")
    last = 0
    first =0
    x = np.arange(1, 6)
    for count,(i, a_b) in zip(count_occurence,enumerate(a_b_runs)):
        last = first + count_occurence[i]
        trace = go.Table(
            type='table',

             # values=[['<b>#(A B)</b><br>'],
                #         ['<b>W size</b><br>']],
                # title='Test for dismantle_efficiency when '+str(a_b_runs),
            header=dict(
                 values=[['<b style="text-align:center;">#</b><br>'],
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
                 values=[x, seperator_size[first:last], percentage_of_dismantle[first:last], largest_CC_size[first:last],
                         connected_component_after_dismantle[first:last]],

                )
            )
        first = last


        layout = dict(
            title='Table test for dismantle efficiency when #AB=' + a_b,
        )
        data = [trace]
        fig = dict(data=data, layout=layout)
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\table_test_for_dismantle_efficiency'+a_b+'.html')


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
    layout = dict(
        title='Table test for Run Time for every #AB',
    )
    data = [trace]
    fig = dict(data=data, layout=layout)
    plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\table_test_run_time.html')


# def create_multiple_charts():
#     output = GetJavaOutput(pathToFile, filename)
#     s_array = output.split('Start')
#     s=[]
#     seperator_and_w_array =[]
#     for idx,val in enumerate(s_array):
#         if idx == 0:
#             continue
#         s.append(val.split('End'))
#     W_for_x = []
#     w_size = []
#     a_b_size = []
#     seperator_size = []
#
#     for idx, val in enumerate(s):
#         if idx == (len(s) - 1):
#             break
#         if idx % 4 == 0:
#             W_for_x.append(val)
#         elif idx % 4 == 1:
#             w_size.append(val)
#         elif idx % 4 == 2:
#             a_b_size.append(val)
#         else:
#             seperator_size.append(val)
#     for val1,val2 in zip(seperator_size, w_size):
#         seperator_and_w_array.append([val1,val2])
#     for val in seperator_and_w_array:
#         print(val)
#     arr = [W_for_x, w_size, a_b_size, seperator_size]
#     # average_arr = []
#     # for i in arr:
#     #     average_arr.append(return_sum(i) / len(i))
#
#     names = ['W_for_x', 'w_size', 'a_b_size', 'seperator_size']
#     # for x in W_for_x:
#     #     print(x, end=" ")
#     # print()
#     # for x in w_size:
#     #     print(x, end=" ")
#     # print()
#     # for x in a_b_size:
#     #     print(x, end=" ")
#     # print()
#     # for x in seperator_size:
#     #     print(x, end=" ")
#     # print()
#     temp_array =[]
#     # temp_array.append(return_avg(seperator_size))
#     avg = return_avg(seperator_size)
#     for i in range(0, 3):
#         #x = [10, 20, 50, 100, 200]
#         # if i == 4:
#         #     continue
#         x = arr[i]
#         for x in range(len(arr[i])):
#             temp_array.append(avg)
#         layout = go.Layout(
#             title='TEST for ' + names[i] + ' and '+names[3]+'<br>'+'#A&B is: ',
#             yaxis=dict(
#                 title=names[3]
#             ),
#             xaxis=dict(
#                 title='' + names[i]
#             )
#         )
#         tracel = go.Scatter(
#             x=arr[i],
#             y=arr[3],
#             mode=' lines + markers',
#             name=names[3] + '(#A&B)',
#             line=dict(
#                 shape='spline'
#             )
#         )
#         trace2 = go.Scatter(
#             x=arr[i],
#             y=temp_array,
#             mode='lines + markers',
#             name='average',
#             line = dict(
#                 shape='spline'
#             )
#         )
#         fig = go.Figure(data=[tracel, trace2], layout=layout)
#         # plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\'+a_b_runs + '__test_separator_and_' + names[i] + '.html')
#         # fig2 = go.Figure(data=[trace2], layout=layout2)
#         # # plotly.offline.plot(fig2,filename='2.html')


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
    # for x in W_for_x:
    #     print(x, end=" ")
    # print()
    # for x in w_size:
    #     print(x, end=" ")
    # print()
    # for x in a_b_size:
    #     print(x, end=" ")
    # print()
    # for x in seperator_size:
    #     print(x, end=" ")
    # print()
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


def create_array(output):
    # output = GetJavaOutput(pathToFile, filename)
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
        # print(val)
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

def create_multiple_charts1(output):
    # output = GetJavaOutput(pathToFile, filename)
    s_array = output.split('Start\n')
    max_cc_array = output.split('Max CC: ')
    max_cc = []
    for idx, val in enumerate(max_cc_array):
        if idx == 0:
            continue
        max_cc.append(val.split('\nMin seperator size:')[0])

    s2 = []
    cc_array = []
    get_cc_array = output.split('Largest CC: ')
    for idx, val in enumerate(get_cc_array):
        if idx == 0:
            continue
        cc_array.append(val.split('\nconnected component After dismentle: ')[0])
    for i, val in enumerate(s_array):
        # print(i)
        # if i != 0:
        #     s3.append(val.split(' pp'))
        s2.append(val.split('End')[0])
    # for i in cc_array:
    #     print(i)
    for idx, val in enumerate(s2):

        if idx == 0:
            continue
        a = val.split(', ')
    seperator_and_cc_array =[]
    W_for_x = []
    w_size = []
    a_b_size = []
    seperator_and_CC_and_w_array = []
    seperator_size = []

    for idx, val in enumerate(a):
        if idx == (len(a) - 1):
            break
        if idx % 4 == 0:
            W_for_x.append(val)
        elif idx % 4 == 1:
            w_size.append(val)
        elif idx % 4 == 2:
            a_b_size.append(val)
        else:
            seperator_size.append(val)
    for val1,val2,val3,val4 in zip(W_for_x, w_size ,seperator_size,cc_array):
        seperator_and_CC_and_w_array.append([int(val1), int(val2),int(val3), int(val4)] )
    seperator_and_w_array = sorted(seperator_and_CC_and_w_array, key=itemgetter(2))
    seperator_and_cc_array = sorted(seperator_and_CC_and_w_array, key=itemgetter(3))
    # for val in seperator_and_CC_and_w_array:
        # print(val)
    x_1 = []
    y_1 = []
    x_2 = []
    y_2 = []
    for val in seperator_and_w_array:
        print(val)
        if val[1] not in x_1:
            y_1.append(val[2])
            x_1.append(val[1])
    seperator_and_cc_array.reverse()
    for val in seperator_and_cc_array:
        if val[1] not in x_2:
            y_2.append(val[3])
            x_2.append(val[1])
    for x, y in zip(x_2,y_2):
        print(str(x)+' ' + str(y))
    arr_x = [x_1, x_2]
    arr_y = [y_1, y_2]
    names_x = ['W size when #AB=200','W size when #AB=200']
    names_y = ['separator_size', ' connected component size']

    # for i in arr_x:
    #     print(i)
    # for x in W_for_x:
    #     print(x, end=" ")
    # print()
    # for x in w_size:
    #     print(x, end=" ")
    # print()
    # for x in a_b_size:
    #     print(x, end=" ")
    # print()
    # for x in seperator_size:
    #     print(x, end=" ")
    # print()
    temp_array =[]
    # temp_array.append(return_avg(seperator_size))
    avg = return_avg(seperator_size)
    for i in range(0, 2):
        #x = [10, 20, 50, 100, 200]
        # if i == 4:
        #     continue
        # x = arr_x[i]
        # for x in range(len(arr[i])):
        #     temp_array.append(avg)
        layout = go.Layout(
            title='TEST for ' + names_x[i] + ' and '+names_y[i]+'<br>',
            yaxis=dict(
                title=names_y[i]
            ),
            xaxis=dict(
                title='' + names_x[i]
            )
        )
        tracel = go.Scatter(
            x=arr_x[i],
            y=arr_y[i],
            mode='lines + markers',
            name=names_x[i] + '()',
            line=dict(
                shape='spline'
            )
        )
        # trace2 = go.Scatter(
        #     x=arr[i],
        #     y=temp_array,
        #     mode='lines + markers',
        #     name='average',
        #     line = dict(
        #         shape='spline'
        #     )
        # )
        fig = go.Figure(data=[tracel], layout=layout)
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\test3.1_'+names_x[i]+'_and_' + names_y[i] + '.html')
        # fig2 = go.Figure(data=[trace2], layout=layout2)
        # # plotly.offline.plot(fig2,filename='2.html')


def create_multiple_charts2(output):

    # output = GetJavaOutput(pathToFile, filename)
    # s_array = output.split('Start\n')
    a_b_size_array = output.split('size of #A_B: ')
    seperator_array = output.split('Min seperator size: ')
    max_cc_array = output.split('Max CC: ')
    max_cc = []
    for idx, val in enumerate(max_cc_array):
        if idx == 0:
            continue
        max_cc.append(val.split('\nMin seperator size:')[0])
    separator_s =[]
    for idx, val in enumerate(seperator_array):
        if idx == 0:
            continue
        separator_s.append(val.split(' EOF')[0])
    a_b_runs =[]
    for idx, val in enumerate(a_b_size_array):
        if idx == 0:
            continue
        a_b_runs.append(val.split(' pp')[0])
    cc_array = []
    get_cc_array = output.split('Largest CC: ')
    for idx, val in enumerate(get_cc_array):
        if idx == 0:
            continue
        cc_array.append(val.split('\nconnected component After dismentle: ')[0])
    arr_x = [a_b_runs,a_b_runs]
    arr_y = [separator_s,max_cc]
    names_x = ['# on AB when w=16', '# on AB when w=16',]
    names_y = ['separator_size', ' connected component size']
    # for i in a_b_runs:
    #     print(i)
    for i in range(0, 2):
        layout = go.Layout(
            title='TEST for ' + names_x[i] + ' and '+names_y[i]+'<br>' ,
            yaxis=dict(
                title=names_y[i]
            ),
            xaxis=dict(
                title='' + names_x[i]
            )
        )
        tracel = go.Scatter(
            x=arr_x[i],
            y=arr_y[i],
            mode='lines + markers',
            name=names_x[i] + '()',
            line=dict(
                shape='spline'
            )
        )
        # trace2 = go.Scatter(
        #     x=arr[i],
        #     y=temp_array,
        #     mode='lines + markers',
        #     name='average',
        #     line = dict(
        #         shape='spline'
        #     )
        # )
        fig = go.Figure(data=[tracel], layout=layout)
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\test3.2_'+names_x[i]+'_and_' + names_y[i] + '.html')
        # fig2 = go.Figure(data=[trace2], layout=layout2)
        # # plotly.offline.plot(fig2,filename='2.html')

# def Avg_1_arg(_name):#form is" number,number"
#     K=3
#     L=600
#     avg=[]
#     AxisX=[]
#     for ratio in frange(2,10,0.2):
#        # print("ratio " , ratio)
#         data=[]
#         AxisX.append(ratio)
#         M = int(L/ratio)
#         for i in range(0,100):
#            # print("i ",i)
#             RandomPositiveCNF(L, M, K)
#             filename="cnf_"+str(L)+"_"+str(M)
#             data=GetJavaOutput(path, filename)
#             os.remove(os.path.join(path,filename))
#         avg.append(FindAVG(data))
#     trace=go.Scatter(
#         x=AxisX,
#         y=avg,
#         name=_name,
#         line=dict(
#             color=('rgb(0,0,0)'),
#             width=4,
#            # dash='dash'
#             )
#         )
#     data=[trace]
#     layout = dict(title = 'run time '+str(L)+" rules",
#             xaxis = dict(title = 'Rules and variables ratio'),
#             yaxis = dict(title = 'Average run time '),
#             )
#     fig = dict(data=data, layout=layout)
#     py.plot(fig, filename='Run time test')

if __name__ == '__main__':
    output = GetJavaOutput(pathToFile, filename)
    create_array(output)
    # printChart(output)
    # create_multiple_charts1(output)
    # create_multiple_charts2(output)

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
# path = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\bin"
# pathToFile = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles\graphTestFiles"
# path1 = "C:/Users\mazma\eclipse-workspace\Modular-Construction-of-Minimal-Models\\testFiles"
#
# def GetJavaOutput(filepath,filename):
#     command = "java -classpath "+path+" Rules/MinimalModel " +filepath+"/"+filename
#     return os.popen(command).read()



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




def return_sum(arr):
    sum_out=0
    for i in range(len(arr)):
        sum_out = int(sum_out) + int(float(arr[i]))
        # sum_out = 0
    return sum_out

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
    print("===============================================")

    for i in range(0, 3):
        #x = [10, 20, 50, 100, 200]
        # if i == 4:
        #     continue
        x = arr[i]
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
            y= (return_sum(arr[i]) / (len(arr[i])+0.00001)),
            mode='lines + markers',
            name='average',
            line = dict(
                shape='spline'
            )
        )
        fig = go.Figure(data=[tracel,trace2], layout=layout)
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\'+str(i) + 'test' + names[i] + '.html')



def parse_to_arrays_and_print_chart(stri):
    output = stri.split("\n")
    W_for_x = []
    w_size = []
    a_b_size = []
    seperator_size = []
    run_time = []
    # print(stri)
    # print("All the vars : ")
    for idx, val in enumerate(output):
        if idx == (len(output)-1):
            break
        if val.find('Memory usage: ') == -1 and val.find('Total Run Time in mili seconds: ') == -1:


            temp = val.split(' ,')
            for t_idx ,j in enumerate(temp):
                if idx == ():
                    continue
                if idx % 4 == 0:
                    W_for_x.append(val)
                elif idx % 4 == 1:
                    w_size.append(val)
                elif idx % 4 == 2:
                    a_b_size.append(val)
                else:
                    seperator_size.append(val)
                # print( val, end="")
    # print('\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++')
    # for idx, val in enumerate(output):
    #     if idx == ():
    #         continue
    #     if idx % 4 == 0:
    #         W_for_x.append(val)
    #     elif idx % 4 == 1:
    #         w_size.append(val)
    #     elif idx % 4 == 2:
    #         a_b_size.append(val)
    #     else:
    #         seperator_size.append(val)
    #     print(val, end="")
    # print('\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++')
    # print("\n W for x")
    # for i in W_for_x:
    #     print(i, end="")
    # print("\nw size")
    # for i in w_size:
    #     print(i, end="")
    # print("\nsize of A B ")
    # for i in a_b_size:
    #     print(i, end="")
    # print("\nseperator size ")
    # for i in seperator_size:
    #     print(i, end="")
    return W_for_x, w_size, a_b_size, seperator_size

def find_between( s, first, last ):
    try:
        start = s.index( first ) + len( first )
        end = s.index( last, start )
        return s[start:end]
    except ValueError:
        return ""

def find_between_r( s, first, last ):
    try:
        start = s.rindex( first ) + len( first )
        end = s.rindex( last, start )
        return s[start:end]
    except ValueError:
        return ""
def get_string_return_arrays(s,idx):

    W_for_x = []
    w_size = []
    a_b_size = []
    seperator_size = []

    for idx, val in enumerate(s):
        if idx == (len(s)-1):
            break
        if idx % 4 == 0:
            W_for_x.append(val)
        elif idx % 4 == 1:
            w_size.append(val)
        elif idx % 4 == 2:
            a_b_size.append(val)
        else:
            seperator_size.append(val)
    arr = [W_for_x,w_size,a_b_size,seperator_size]
    names= ['W_for_x','w_size','a_b_size','seperator_size']
    for x in W_for_x:
        print(x,end=" ")
    print()
    for x in w_size:
        print(x,end=" ")
    print()
    for x in a_b_size:
        print(x,end=" ")
    print()
    for x in seperator_size:
        print(x,end=" ")
    print()
    print("===============================================")
    #
    # for i in range(0,4):
    #     x = [10, 20, 50, 100, 200]

    return W_for_x,w_size,a_b_size,seperator_size


def create_array():
    output = '''
Start
16, 15, 1, 24, 16, 15, 3, 32, 
End
size of #A_B: 5 pp
Memory usage: 1.1465072631835938 MB
Total Run Time in mili seconds: 2172 sec
Min seperator size: 24 EOF
Start
16, 13, 0, 22, 16, 13, 2, 28, 16, 13, 3, 23, 16, 13, 5, 21, 16, 13, 7, 20, 16, 13, 8, 29, 32, 27, 1, 25, 32, 27, 4, 24, 32, 27, 5, 19, 32, 27, 6, 15, 32, 27, 7, 20, 32, 27, 8, 24, 
End
size of #A_B: 10 pp
Memory usage: 1.6736373901367188 MB
Total Run Time in mili seconds: 3604 sec
Min seperator size: 15 EOF
Start
16, 13, 4, 19, 16, 13, 7, 32, 16, 13, 9, 28, 16, 13, 10, 29, 16, 13, 11, 26, 16, 13, 12, 25, 16, 13, 13, 28, 16, 13, 15, 26, 16, 13, 16, 22, 32, 27, 0, 21, 32, 27, 1, 21, 32, 27, 2, 22, 32, 27, 3, 16, 32, 27, 4, 16, 32, 27, 5, 23, 32, 27, 6, 23, 32, 27, 7, 18, 32, 27, 8, 18, 32, 27, 9, 22, 32, 27, 11, 23, 32, 27, 12, 19, 32, 27, 14, 25, 32, 27, 15, 22, 32, 27, 16, 23, 32, 27, 18, 24, 32, 27, 19, 17, 
End
size of #A_B: 20 pp
Memory usage: 2.3898544311523438 MB
Total Run Time in mili seconds: 9020 sec
Min seperator size: 16 EOF
Start
16, 14, 3, 28, 16, 14, 4, 15, 16, 14, 5, 14, 16, 14, 7, 27, 16, 14, 9, 26, 16, 14, 11, 19, 16, 14, 12, 18, 16, 14, 15, 24, 16, 14, 16, 15, 16, 14, 17, 32, 16, 14, 20, 29, 16, 14, 23, 30, 16, 14, 24, 25, 16, 14, 27, 27, 16, 14, 35, 13, 16, 14, 37, 28, 16, 14, 39, 16, 16, 14, 41, 21, 16, 14, 43, 18, 16, 14, 46, 17, 32, 26, 0, 21, 32, 26, 3, 26, 32, 26, 4, 15, 32, 26, 7, 20, 32, 26, 9, 25, 32, 26, 10, 27, 32, 26, 11, 20, 32, 26, 12, 19, 32, 26, 13, 21, 32, 26, 15, 15, 32, 26, 17, 24, 32, 26, 18, 21, 32, 26, 20, 16, 32, 26, 21, 17, 32, 26, 22, 21, 32, 26, 23, 23, 32, 26, 24, 18, 32, 26, 26, 23, 32, 26, 27, 20, 32, 26, 30, 14, 32, 26, 31, 22, 32, 26, 32, 16, 32, 26, 33, 19, 32, 26, 35, 26, 32, 26, 36, 22, 32, 26, 37, 25, 32, 26, 38, 22, 32, 26, 39, 22, 32, 26, 40, 22, 32, 26, 41, 23, 32, 26, 42, 23, 32, 26, 44, 25, 32, 26, 46, 26, 
End
size of #A_B: 50 pp
Memory usage: 3.7002410888671875 MB
Total Run Time in mili seconds: 17473 sec
Min seperator size: 13 EOF
Start
16, 15, 1, 14, 16, 15, 2, 22, 16, 15, 6, 20, 16, 15, 8, 22, 16, 15, 14, 22, 16, 15, 20, 17, 16, 15, 21, 23, 16, 15, 23, 21, 16, 15, 24, 16, 16, 15, 25, 21, 16, 15, 27, 20, 16, 15, 28, 23, 16, 15, 33, 22, 16, 15, 37, 22, 16, 15, 39, 22, 16, 15, 40, 26, 16, 15, 47, 28, 16, 15, 48, 24, 16, 15, 50, 20, 16, 15, 51, 14, 16, 15, 53, 23, 16, 15, 54, 23, 16, 15, 56, 18, 16, 15, 58, 21, 16, 15, 60, 19, 16, 15, 61, 21, 16, 15, 63, 22, 16, 15, 64, 27, 16, 15, 65, 21, 16, 15, 66, 18, 16, 15, 68, 22, 16, 15, 69, 22, 16, 15, 71, 20, 16, 15, 72, 19, 16, 15, 73, 15, 16, 15, 75, 21, 16, 15, 76, 20, 16, 15, 77, 21, 16, 15, 80, 21, 16, 15, 81, 25, 16, 15, 83, 24, 16, 15, 85, 19, 16, 15, 86, 15, 16, 15, 88, 21, 16, 15, 89, 22, 16, 15, 92, 27, 16, 15, 93, 19, 16, 15, 95, 16, 16, 15, 97, 27, 32, 27, 3, 17, 32, 27, 8, 25, 32, 27, 9, 21, 32, 27, 11, 21, 32, 27, 17, 19, 32, 27, 26, 27, 32, 27, 30, 17, 32, 27, 31, 22, 32, 27, 34, 18, 32, 27, 35, 24, 32, 27, 50, 20, 32, 27, 51, 17, 32, 27, 56, 22, 32, 27, 60, 23, 32, 27, 70, 23, 32, 27, 81, 20, 32, 27, 82, 25, 32, 27, 84, 23, 32, 27, 90, 22, 32, 27, 94, 21, 32, 27, 98, 26, 
End
size of #A_B: 100 pp
Memory usage: 4.5681610107421875 MB
Total Run Time in mili seconds: 28654 sec
Min seperator size: 14 EOF
Start
16, 15, 1, 17, 16, 15, 3, 21, 16, 15, 4, 20, 16, 15, 5, 24, 16, 15, 7, 15, 16, 15, 8, 20, 16, 15, 9, 24, 16, 15, 12, 23, 16, 15, 14, 19, 16, 15, 17, 22, 16, 15, 18, 23, 16, 15, 20, 13, 16, 15, 22, 21, 16, 15, 23, 17, 16, 15, 25, 24, 16, 15, 26, 22, 16, 15, 27, 26, 16, 15, 28, 28, 16, 15, 29, 19, 16, 15, 32, 25, 16, 15, 33, 19, 16, 15, 35, 17, 16, 15, 37, 20, 16, 15, 38, 20, 16, 15, 40, 19, 16, 15, 42, 25, 16, 15, 43, 16, 16, 15, 44, 16, 16, 15, 45, 20, 16, 15, 46, 18, 16, 15, 47, 23, 16, 15, 48, 19, 16, 15, 49, 17, 16, 15, 50, 18, 16, 15, 51, 21, 16, 15, 52, 20, 16, 15, 54, 22, 16, 15, 56, 16, 16, 15, 60, 19, 16, 15, 61, 21, 16, 15, 62, 28, 16, 15, 63, 22, 16, 15, 65, 18, 16, 15, 66, 26, 16, 15, 67, 21, 16, 15, 70, 18, 16, 15, 74, 25, 16, 15, 75, 20, 16, 15, 76, 17, 16, 15, 81, 20, 16, 15, 83, 21, 16, 15, 84, 21, 16, 15, 85, 16, 16, 15, 86, 20, 16, 15, 88, 19, 16, 15, 93, 20, 16, 15, 95, 19, 16, 15, 96, 22, 16, 15, 97, 25, 16, 15, 99, 19, 16, 15, 102, 24, 16, 15, 103, 24, 16, 15, 105, 20, 16, 15, 106, 19, 16, 15, 108, 20, 16, 15, 110, 25, 16, 15, 111, 21, 16, 15, 112, 19, 16, 15, 113, 18, 16, 15, 114, 21, 16, 15, 117, 27, 16, 15, 119, 27, 16, 15, 120, 28, 16, 15, 121, 19, 16, 15, 123, 25, 16, 15, 124, 18, 16, 15, 125, 19, 16, 15, 126, 25, 16, 15, 127, 19, 16, 15, 128, 19, 16, 15, 129, 27, 16, 15, 130, 21, 16, 15, 136, 27, 16, 15, 140, 19, 16, 15, 142, 18, 16, 15, 143, 22, 16, 15, 144, 23, 16, 15, 148, 24, 16, 15, 149, 20, 16, 15, 150, 23, 16, 15, 153, 21, 16, 15, 155, 25, 16, 15, 156, 22, 16, 15, 157, 20, 16, 15, 158, 22, 16, 15, 160, 19, 16, 15, 162, 25, 16, 15, 163, 18, 16, 15, 166, 18, 16, 15, 169, 19, 16, 15, 172, 21, 16, 15, 173, 21, 16, 15, 174, 18, 16, 15, 175, 19, 16, 15, 181, 23, 16, 15, 182, 23, 16, 15, 183, 22, 16, 15, 184, 18, 16, 15, 185, 21, 16, 15, 187, 23, 16, 15, 190, 12, 16, 15, 192, 25, 16, 15, 194, 18, 16, 15, 195, 23, 16, 15, 196, 20, 16, 15, 199, 19, 32, 31, 0, 23, 32, 31, 1, 28, 32, 31, 2, 24, 32, 31, 3, 24, 32, 31, 4, 22, 32, 31, 5, 21, 32, 31, 6, 21, 32, 31, 7, 28, 32, 31, 8, 20, 32, 31, 9, 23, 32, 31, 10, 22, 32, 31, 11, 21, 32, 31, 12, 22, 32, 31, 13, 22, 32, 31, 14, 20, 32, 31, 15, 26, 32, 31, 16, 28, 32, 31, 17, 20, 32, 31, 18, 27, 32, 31, 19, 23, 32, 31, 20, 24, 32, 31, 21, 27, 32, 31, 22, 26, 32, 31, 23, 24, 32, 31, 24, 24, 32, 31, 25, 23, 32, 31, 26, 25, 32, 31, 27, 17, 32, 31, 28, 19, 32, 31, 29, 24, 32, 31, 30, 27, 32, 31, 31, 25, 32, 31, 32, 15, 32, 31, 33, 26, 32, 31, 34, 15, 32, 31, 35, 21, 32, 31, 36, 23, 32, 31, 37, 21, 32, 31, 38, 24, 32, 31, 39, 25, 32, 31, 40, 19, 32, 31, 41, 25, 32, 31, 42, 23, 32, 31, 43, 26, 32, 31, 44, 21, 32, 31, 45, 21, 32, 31, 46, 21, 32, 31, 47, 26, 32, 31, 48, 28, 32, 31, 50, 25, 32, 31, 51, 25, 32, 31, 52, 25, 32, 31, 53, 26, 32, 31, 54, 25, 32, 31, 55, 30, 32, 31, 56, 24, 32, 31, 57, 20, 32, 31, 58, 26, 32, 31, 59, 27, 32, 31, 61, 22, 32, 31, 62, 24, 32, 31, 63, 21, 32, 31, 64, 22, 32, 31, 65, 20, 32, 31, 66, 22, 32, 31, 67, 23, 32, 31, 68, 23, 32, 31, 69, 20, 32, 31, 70, 22, 32, 31, 72, 22, 32, 31, 73, 29, 32, 31, 74, 21, 32, 31, 75, 22, 32, 31, 76, 26, 32, 31, 77, 23, 32, 31, 78, 27, 32, 31, 79, 22, 32, 31, 80, 27, 32, 31, 81, 20, 32, 31, 82, 22, 32, 31, 83, 22, 32, 31, 84, 25, 32, 31, 85, 17, 32, 31, 86, 23, 32, 31, 87, 21, 32, 31, 88, 25, 32, 31, 89, 26, 32, 31, 90, 20, 32, 31, 91, 19, 32, 31, 92, 22, 32, 31, 93, 25, 32, 31, 94, 24, 32, 31, 95, 23, 32, 31, 96, 24, 32, 31, 97, 22, 32, 31, 98, 27, 32, 31, 99, 22, 32, 31, 101, 21, 32, 31, 102, 20, 32, 31, 104, 26, 32, 31, 105, 23, 32, 31, 106, 20, 32, 31, 107, 29, 32, 31, 108, 24, 32, 31, 110, 26, 32, 31, 111, 22, 32, 31, 112, 28, 32, 31, 113, 16, 32, 31, 114, 20, 32, 31, 115, 22, 32, 31, 116, 23, 32, 31, 117, 26, 32, 31, 118, 21, 32, 31, 119, 27, 32, 31, 120, 22, 32, 31, 122, 22, 32, 31, 123, 28, 32, 31, 124, 16, 32, 31, 125, 25, 32, 31, 126, 24, 32, 31, 127, 19, 32, 31, 128, 26, 32, 31, 129, 24, 32, 31, 130, 23, 32, 31, 131, 23, 32, 31, 132, 21, 32, 31, 133, 23, 32, 31, 134, 25, 32, 31, 135, 26, 32, 31, 136, 21, 32, 31, 137, 27, 32, 31, 138, 23, 32, 31, 139, 20, 32, 31, 140, 23, 32, 31, 141, 17, 32, 31, 142, 24, 32, 31, 143, 17, 32, 31, 144, 24, 32, 31, 145, 25, 32, 31, 146, 21, 32, 31, 147, 23, 32, 31, 148, 24, 32, 31, 149, 25, 32, 31, 150, 22, 32, 31, 151, 23, 32, 31, 152, 23, 32, 31, 153, 21, 32, 31, 154, 25, 32, 31, 155, 20, 32, 31, 156, 24, 32, 31, 157, 23, 32, 31, 158, 22, 32, 31, 159, 22, 32, 31, 160, 25, 32, 31, 161, 29, 32, 31, 162, 18, 32, 31, 164, 21, 32, 31, 165, 24, 32, 31, 166, 28, 32, 31, 168, 24, 32, 31, 169, 24, 32, 31, 170, 24, 32, 31, 171, 19, 32, 31, 172, 26, 32, 31, 173, 24, 32, 31, 174, 25, 32, 31, 175, 27, 32, 31, 176, 23, 32, 31, 177, 21, 32, 31, 178, 24, 32, 31, 179, 23, 32, 31, 180, 22, 32, 31, 181, 20, 32, 31, 182, 25, 32, 31, 183, 21, 32, 31, 184, 21, 32, 31, 185, 28, 32, 31, 186, 31, 32, 31, 187, 23, 32, 31, 188, 28, 32, 31, 190, 26, 32, 31, 191, 23, 32, 31, 192, 21, 32, 31, 193, 21, 32, 31, 194, 26, 32, 31, 196, 25, 32, 31, 197, 21, 32, 31, 198, 24, 32, 31, 199, 28, 
End
size of #A_B: 200 pp
Memory usage: 15.945304870605469 MB
Total Run Time in mili seconds: 104491 sec
Min seperator size: 12 EOF
    '''
    a_b_runs_array = output.split('size of #A_B: ')
    a_b_runs = []
    for idx, val in enumerate(a_b_runs_array):
        if idx == 0:
            continue
        a_b_runs.append(val.split(' pp')[0])

    test = output.split('Start')
    s2 = []
    for i in test:
        # print(i)
        s2.append(i.split('End')[0])
    for idx, val in enumerate(s2):
        a = val.split(', ')
        if (idx < len(a_b_runs)):
            get_string_print_chart(a, a_b_runs[idx])
        # get_string_print_chart(a)

        # for x in a:
        #     print(x, end=" ")
        a.clear()
        # print("------------------------------------------------------")
    mem_array_usage = output.split('Memory usage: ')
    mem_array = []
    run_time_array_usage = output.split('Total Run Time in mili seconds: ')
    min_separator_array = output.split('Min seperator size: ')
    # a_b_runs_array = output.split('size of #A_B: ')
    # print("tttttttttttttttttttttttt "+mem_array_usage[0])
    run_time = []
    # a_b_runs = []
    min_separator = []
    # print("--------------------------------------------------------------------")
    for idx,val in enumerate(mem_array_usage):
        if idx == 0:
            continue
        mem_array.append(val.split(' MB')[0])
        # parse_to_arrays_and_print_chart(i)
    for idx,val in enumerate(run_time_array_usage):
        if idx == 0:
            continue
        run_time.append(val.split(' sec')[0])
    for idx, val in enumerate(min_separator_array):
        if idx == 0:
            continue
        min_separator.append(val.split(' EOF')[0])
    # for idx, val in enumerate(a_b_runs_array):
    #     if idx == 0:
    #         continue
    #     a_b_runs.append(val.split(' pp')[0])
        # print(i +'\n ^^^^^^^^^^^^^^^^^^^^')
    # for i in mem_array:
    # print(i +'^^^^^^^^^^^^^^^^^^^^^^')
    # print("\n--------------------------------------------------------------------")
    # for i in run_time:
    #     print(i +'\n ========================')
    # for i in a_b_runs:
    #     print(i,end=' ')
    # print("--------------------------------------------------------------------")
    #
    # for i in min_separator:
    #     print(i, end=" ")
    # print("\n--------------------------------------------------------------------")
    print_table(mem_array, run_time ,min_separator,a_b_runs)
    return mem_array, run_time,min_separator, a_b_runs





def printChart():
    # W_for_x_array, w_size_array, a_b_array, seperator_array = create_array()
    mem_array_usage, run_time_array,min_separator,a_b_runs = create_array()
    # x = np.linspace(2, 32)
    # x= w_size_array
    x = [5,10 , 20 , 50, 100, 200]
    res =[mem_array_usage, run_time_array,min_separator]
    dic = ['mem usage in MB','runtime in mili','min size of separator' ]
    dic2=['memory Usage','run Time','separator Size']
    for idx in range(0,3):
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
        plotly.offline.plot(fig, filename='testFiles\graphTestFiles\\' +dic2[idx]+ '.html')
    # layout = go.Layout(
    #     title = 'TEST1' ,
    #     yaxis= dict(
    #         title = 'mem usage in MB'
    #     ),
    #     xaxis= dict(
    #         title = '# of A&B'
    #     )
    # )
    # tracel = go.Scatter(
    #     x = x,
    #     y = mem_array_usage,
    #     mode = ' lines + markers',
    #     name= 'memoryUsage(#A&B)',
    #     line = dict(
    #         shape = 'spline'
    #     )
    # )
    # layout2 = go.Layout(
    #     title = 'TEST2',
    #     yaxis= dict(
    #         title = 'runtime in mili'
    #     ),
    #     xaxis= dict(
    #         title = ' # of A&B'
    #     )
    # )
    # trace2 = go.Scatter(
    #     x =x,
    #     y = run_time_array,
    #     mode = ' lines + markers',
    #        name = 'runTime(#A&B)',
    #               line = dict(
    #     shape='spline'
    # )
    # )

    # fig2 = go.Figure(data=[trace2], layout=layout2)
    # plotly.offline.plot(fig2,filename='2.html')


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
    temp_mem =[]
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
            values=[a_b_runs, temp_mem, temp_run, min_separator],

        )
    )

    data = [trace]
    plotly.offline.plot(data,filename='testFiles\graphTestFiles\\table.html')


if __name__ == '__main__':
    printChart()
    # print_table()

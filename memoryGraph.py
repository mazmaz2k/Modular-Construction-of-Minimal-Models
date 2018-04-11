'''
Created on Apr 10, 2018

@author: adi tayri
'''
import os
import io
import random

import plotly
import plotly.plotly as py
import plotly.graph_objs as go
import subprocess
plotly.tools.set_credentials_file(username='adita', api_key='T5ZzZ3SsaTXrHcAVwbJU')
from subprocess import STDOUT,PIPE



PATH_TO_MINIMAL_MODEL="/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/bin"
def GetJavaOutput(filepath,filename):
    command = "java -classpath "+PATH_TO_MINIMAL_MODEL+" Rules/MinimalModel "+filepath+"/"+filename
    return os.popen(command).read()
# 
# s= GetMemoryFromJava(".//CnfFile.txt", "").split(",")
# print(s[0])
path="/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/testFiles"
# def RandomPositiveCNF(L , M , K):
#     directory="/"+str(M)+"variables"
#     filename="cnf_"+str(L)+"_"+str(M)
#     filepath=os.path.join(path+directory,filename)
#     myFile=io.FileIO(filepath, "w") 
#     myFile.write(str(L)+"\n")
#     for i in range(0,L):  
#         line=""
#         allNegative=True
#         for j in range(0, K):
#             var=random.randint(1,M)
#             if(j!=K-1 or not allNegative):
#                 if(random.getrandbits(1)):
#                     var=var*-1    
#             if(var>0):
#                 allNegative=False            
#             line += str(var)+" "
#         line+="0" 
#         myFile.write(line+"\n")

def RandomPositiveCNF(L , M , K):
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+"\n")
    for i in range(0,L):  
        line=""
        allNegative=True
        for j in range(0, K):
            var=random.randint(1,M)
            if(j!=K-1 or not allNegative):
                if(random.getrandbits(1)):
                    var=var*-1    
            if(var>0):
                allNegative=False            
            line += str(var)+" "
        line+="0" 
        myFile.write(line+"\n")
    return myFile
    
def frange(start, stop, step):
    i = start
    while i < stop:
        yield i
        i += step

def FindAVG(arr):
    s=0.0
    size=len(arr)
    if size == 0:
        return
    for i in arr:
        fi=float(i)
        s=s+fi
    avg=s/size
    return avg   
     
# def memoryTest():
#     K = 3 #K-SAT
#     variableList=[20,40,50]
#     data=[]
#     for M in variableList:
#         directory="/"+str(M)+"variables"
#         filepath=os.path.join(path+directory)
#         avgList=[]
#         AxisX=[]
#         ratio=2
#         for ratio in frange(2,9,0.2):
#             MemoryList=[]
#             AxisX.append(ratio)
#             L = int(ratio*M)
#             for i in range(0,5):
#                 RandomPositiveCNF(L, M, K)
#                 filename="cnf_"+str(L)+"_"+str(M)
#                 memInMB=GetMemoryFromJava(filepath, filename)
#                 MemoryList.append(memInMB)
#                 os.remove(os.path.join(filepath,filename))
#             avg=FindAVG(MemoryList)
#             avgList.append(avg) 
#         #print(avgList)      
#         trace = go.Scatter(
#             x = AxisX,
#             y = avgList,
#             name = str(M)+"variables",
#             line = dict(
#                 color = ("rgb("+str(random.randint(1,256))+","+str(random.randint(1,256))+","+str(random.randint(1,256))+")"),
#                 width = 4)
#             )
#         data.append(trace)   
#     layout = dict(title = 'chart',
#               xaxis = dict(title = 'Rules and variables ratio'),
#               yaxis = dict(title = 'MemoryInMB'),
#               )
#  
#     fig = dict(data=data, layout=layout)
#     
#     py.plot(fig, filename='waspAvgMem')     



def memoryTest():
    K=3
    M=5
    avgListWASP=[]
    avgListModuMin=[]
    AxisX=[]
    for ratio in frange(2,4,0.2):
        print("ratio " , ratio)
        WASPMemoryList=[]
        MosuMinMemoryList=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        wasp_list=[]
        modumin_list=[]
        for i in range(0,1): 
            print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            memList=GetMemoryFromJava(path, filename).split(",")
            print(memList)
            WASPmemInMB=memList[0]
            print(WASPmemInMB)
            wasp_list.append(WASPmemInMB)
            ModuMinmemInMB=memList[1]
            print(ModuMinmemInMB)
            modumin_list.append(ModuMinmemInMB)
            os.remove(os.path.join(path,filename))
        avgListWASP.append(FindAVG(wasp_list)) 
        avgListModuMin.append(FindAVG(modumin_list))
        print(avgListWASP)
        print(avgListModuMin)
#     trace1=go.Scatter(
#         x=AxisX,
#         y=avgListWASP,
#         name="memory usage wasp",
#         line=dict(
#             color=('rgb(0,0,0)'),
#             width=4,
#             dash='dash'
#             )
#         )
#     trace2=go.Scatter(
#         x=AxisX,
#         y=avgListWASP,
#         name="memory usage modumin",
#         line=dict(
#             color=('rgb(0,0,0)'),
#             width=4,
#             dash='dot'
#             )
#         )
#     data=[trace1,trace2]
#     layout = dict(title = 'memory usage',
#             xaxis = dict(title = 'Rules and variables ratio'),
#             yaxis = dict(title = 'MemoryInMB'),
#             )
#     fig = dict(data=data, layout=layout)  
#     py.plot(fig, filename='memory test') 
    
    
    
    
    
    
    
    
def testEverage(): 
    K = 3 #K-SAT
    variableList=[20,40,50]
    data=[]
    for M in variableList:
        directory="/"+str(M)+"variables"
        filepath=os.path.join(path+directory)
        AverageList=[]
        AxisX=[]
        ratio=2
        for ratio in frange(2,9,0.2):
            sourceSizeList=[]
            AxisX.append(ratio)
            L = int(ratio*M)
            for i in range(0,1):
                RandomPositiveCNF(L, M, K)
                filename="cnf_"+str(L)+"_"+str(M)
                size=GetJavaOutput(filepath, filename)
                sourceSizeList.append(size)
                os.remove(os.path.join(filepath,filename))
            avg=FindAVG(sourceSizeList)  
            AverageList.append(avg)   
        trace = go.Scatter(
            x = AxisX,
            y = AverageList,
            name = str(M)+"variabls",
            line = dict(
                color = ("rgb("+str(random.randint(1,256))+","+str(random.randint(1,256))+","+str(random.randint(1,256))+")"),
                width = 4)
            )
        data.append(trace)   
    layout = dict(title = 'test average',
              xaxis = dict(title = 'Rules and variables ratio'),
              yaxis = dict(title = 'Average Source Size'),
              )
 
    fig = dict(data=data, layout=layout)
    py.plot(fig, filename='averagesourcesize')    
    
    
    
#testEverage()   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
def checkModuMin():
    K=3
    M=100
    for ratio in frange(1,20,0.2):
        L = int(ratio*M)
        myFile=RandomPositiveCNF(L, M, K)   
        filename="cnf_"+str(L)+"_"+str(M)
        output=GetJavaOutput(path, filename)
        print("ratio "+ str(ratio) +"min model: " +output)
        
#checkModuMin()   
#RandomPositiveCNF(150, 50, 3)        
#memoryTest()
# RandomPositiveCNF(150, 50, 3)
# s=GetMemoryFromJava("/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/testFiles/50variables", "cnf_150_50")
# print(s)






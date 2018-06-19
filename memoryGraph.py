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
    myFile.write(str(L)+" "+str(M)+"\n")
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

def RandomPositiveCNF2(L , M , K):
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+" "+str(M)+"\n")
    for i in range(0,L):  
        line=""
        allNegative=True
        for j in range(0, K):
            var=random.randint(1,M)     
            line += str(var)+" "
        line+="0" 
        myFile.write(line+"\n")
    return myFile
def RandomPositiveCNF3(L , M , K, percentNegated):
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+" "+str(M)+"\n")
    for i in range(0,L):  
        line=""
        allNegative=True
        for j in range(0, K):
            var=random.randint(1,M)
            if(j!=K-1 or not allNegative):
                r=random.randrange(1,100)
                if(percentNegated>=r):
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
     
        
def FindMedian(arr):
    median=0.0
    size = len(arr)  
    if size == 0:
        return
    arr.sort(cmp=None, key=None, reverse=False) 
    if size%2 == 0:
        num1=float(arr[(size-2)/2])
        num2=float(arr[size/2])
        median=(num1+num2)/2
    else:
        middle=(size-1)/2
        median = arr[middle] 
        
    return median   
    
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
    
    
   
def Avg_2_args(first_name,second_name):#form is" number,number"
    K=3
    L= 100
    avg1=[]
    avg2=[]
    AxisX=[]
    for ratio in frange(4,6,0.2):
       # print("ratio " , ratio)
        data1=[]
        data2=[]
        AxisX.append(ratio)
        M = int(L/ratio)
        for i in range(0,10): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename).split(",")
            data1.append(output[0])
            data2.append(output[1])
            os.remove(os.path.join(path,filename))
        avg1.append(FindAVG(data1)) 
        avg2.append(FindAVG(data2))
    trace1=go.Scatter(
        x=AxisX,
        y=avg1,
        name=first_name,
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=avg2,
        name = second_name,
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'run time '+str(L)+" rules",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'Average run time '),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Run time test') 
    
 
 
def Avg_1_arg(_name):#form is" number,number"
    K=3
    L=300
    avg=[]
    AxisX=[]
    for ratio in frange(2,9,0.2):
       # print("ratio " , ratio)
        data=[]
        AxisX.append(ratio)
        M = (int(L/ratio))
        for i in range(0,200): 
           # print("i ",i)
            RandomPositiveCNF2(L, M, K )
            filename="cnf_"+str(L)+"_"+str(M)
            data.append(GetJavaOutput(path, filename))
            os.remove(os.path.join(path,filename))
        avg.append(FindAVG(data)) 
    trace=go.Scatter(
        x=AxisX,
        y=avg,
        name=_name,
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    data=[trace]
    layout = dict(title = 'run time  '+str(L)+" rules" + str(M)+ " vars",
            xaxis = dict(title = 'Rules variables ratio'),
            yaxis = dict(title = 'Average runtime  '),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='RatioMaxsourceSize')  
   
   
def testMemUsage():
    K=3
    M=100
    avgMemWASP=[]
    avgModuMinUsingWASP=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
       # print("ratio " , ratio)
        mem_WASP=[]
        mem_ModuMinUsingWASP=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        for i in range(0,80): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename).split(",")
           # print(output)
            mem_WASP.append(output[0])
            mem_ModuMinUsingWASP.append(output[1])
            os.remove(os.path.join(path,filename))
        avgMemWASP.append(FindAVG(mem_WASP)) 
        avgModuMinUsingWASP.append(FindAVG(mem_ModuMinUsingWASP))
    trace1=go.Scatter(
        x=AxisX,
        y=avgMemWASP,
        name="WASP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=avgModuMinUsingWASP,
        name="ModuMin using wasp",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'Memory usage in MB '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'Memory usage in MB'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Memory usage test')  
   
   
   
   
   
   
def avgSource():
    K=3
    M=100
    avgSourceSize=[]
    medSourceSize=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
        sourceSize=[]
        AxisX.append(ratio)
        L = int(ratio*M)
        for i in range(0,3): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename)
            sourceSize.append(output)
            os.remove(os.path.join(path,filename))
        avgSourceSize.append(FindAVG(sourceSize)) 
        medSourceSize.append(FindMedian(sourceSize))
    trace1=go.Scatter(
        x=AxisX,
        y=avgSourceSize,
        name="Average source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2=go.Scatter(
        x=AxisX,
        y=medSourceSize,
        name="median source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dash'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'Average and median source size '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'source size'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Source size test')    
   
   

def avgSource2():
    K=3
    L=600
    avgSourceSize=[]
    medSourceSize=[]
    AxisX=[]
    for ratio in frange(1,25,0.2):
        sourceSize=[]
        AxisX.append(ratio)
        M = int(L/ratio)
        for i in range(0,300): 
           # print("i ",i)
            RandomPositiveCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename)
            sourceSize.append(output)
            os.remove(os.path.join(path,filename))
        avgSourceSize.append(FindAVG(sourceSize)) 
        medSourceSize.append(FindMedian(sourceSize))
    trace1=go.Scatter(
        x=AxisX,
        y=avgSourceSize,
        name="Average source size",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
   
    data=[trace1]
    layout = dict(title = 'Average source size '+str(L)+" rules",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'source size'),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Source size test')    

#avgSource2()       
#testMemUsage() 

def printSources():
    K=3
    M=280
    L=600
    RandomPositiveCNF(L, M, K)
    filename="cnf_"+str(L)+"_"+str(M)
    output=GetJavaOutput(path, filename)
    print(output)
    
    
def checkModuMin():
    K=3
    L=100
    for ratio in frange(4,6,0.2):
        M = int(L/ratio)
        for i in range(5): 
            myFile=RandomPositiveCNF(L, M, K)   
            filename="cnf_"+str(L)+"_"+str(M)
            output=GetJavaOutput(path, filename)
            print(output)
        
def doChart():
    trace1 = go.Scatter(
     x=[2, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2, 3.4, 3.6, 3.8, 4.0, 4.2, 4.4, 4.6, 4.8, 5.0, 5.2, 5.4, 5.6, 5.8, 6.0, 6.2, 6.4, 6.6, 6.8, 7.0, 7.2, 7.4, 7.6, 7.8, 8.0, 8.2, 8.4, 8.6, 8.8], 
     y= [74.117183915, 62.498461955, 61.582453755, 59.981584095, 60.022266415, 58.829127105, 58.51602863, 58.190380475, 58.129226935, 57.5149512, 57.49328256, 56.884887755, 57.048288025, 57.48448717, 55.9553929, 55.791308655, 55.688780505, 55.563295365, 55.100272385, 55.043753885, 54.4075368, 54.77236914, 53.73625775, 53.73727822, 54.411623435, 53.419758975, 53.181346975, 53.36205153, 53.120946365, 53.15292205, 53.076367965, 53.01573312, 52.597877855, 52.563858355, 52.40401561], 
        name="DP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
    trace2 = go.Scatter(
     x=   [2, 2.2, 2.4, 2.6, 2.8, 3.0, 3.2, 3.4, 3.6, 3.8, 4.0, 4.2, 4.4, 4.6, 4.8, 5.0, 5.2, 5.4, 5.6, 5.8, 6.0, 6.2, 6.4, 6.6, 6.8, 7.0, 7.2, 7.4, 7.6, 7.8, 8.0, 8.2, 8.4, 8.6, 8.8], 
     y=[65.12934675, 63.459217985, 59.94361905, 59.443142165, 57.65448288, 55.581093245, 54.05232221, 52.920719215, 53.547176265, 53.1088279, 52.00473471, 50.42837971, 50.498201375, 50.002758365, 48.91225308, 48.48771212, 48.12938122, 47.80164537, 47.31772599, 47.19398231, 47.307130925, 47.6645041, 46.37947809, 46.200340005, 45.91929904, 45.98676114, 45.29718316, 45.11968752, 45.174940475, 44.627244165, 45.125017935, 44.728667415, 44.341634885, 44.077321605, 44.53311118], 
        name="ModuMin DP",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
            dash='dot'
            )
        )
    data=[trace1,trace2]
    layout = dict(title = 'run time '+"300"+" rules",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'AverageRunTime '),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Run time test')  

if __name__ =="__main__":
    #Avg_2_args("ModuMin DP", "ratio source size / vars num")
  # Avg_1_arg("moduDP")
   #printSources()
   checkModuMin()
   #doChart()
  # RandomPositiveCNF3(500, 120, 3,1)

            
        
#checkModuMin()   
#RandomPositiveCNF(150, 50, 3)        
#memoryTest()
# RandomPositiveCNF(150, 50, 3)
# s=GetMemoryFromJava("/home/rachel/Desktop/Modular-Construction-of-Minimal-Models/testFiles/50variables", "cnf_150_50")
# print(s)





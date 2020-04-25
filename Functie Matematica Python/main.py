from tkinter import *
from decimal import *
import math
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
from matplotlib.figure import Figure
import numpy as np
import matplotlib.pyplot as plt

getcontext().prec = 200


class Gui(object):
    def f(self, arg):
        return np.sin(self.b * arg)

    def creeazaGrafic(self):
        '''
        root = Tk()
        root.title("Grafic")
        q=[]
        x=np.arange(self.x0,self.it,0.01)
        y=np.sin(x)
        verfunc=np.vectorize(self.f)
        T=verfunc(x)
        fig = Figure(figsize=(9,9))
        a= fig.add_subplot(111)
        a.set_title("f(x)=x+a*sin(b*x)")
        a.set_xlabel("X",fontsize=15)
        a.set_ylabel("Y",fontsize=15)
        a.plot(self.listaCalculeGrafic,'o')
        a.plot(x,x+self.a*T)
        a.legend(['x_n+1=x_n+a*sin(b*x)','f(x)=x+a*sin(b*x)'])
        a.grid(linestyle='--')
        canvas = FigureCanvasTkAgg(fig,master=root)
        canvas.get_tk_widget().pack()
        canvas.draw()
        '''
        '''root2 = Tk()'''
        self.fig2.clear()
        x = np.arange(self.capatIntervalMinim - 1, self.capatIntervalMaxim + 1, 0.01)
        intersectieSY = np.arange(0.0, self.intersectieSusY, 0.01)
        intersectieJY = np.arange(0.0, self.intersectieJosY, 0.01)
        capatStgY = np.arange(0.0, self.capatIntervalMinim, 0.01)
        capatDrY = np.arange(0.0, self.capatIntervalMaxim, 0.01)
        capatMijY = np.arange(0.0, self.mijlocInterval, 0.01)
        intersectieSX = []
        intersectieJX = []
        capatStgX = []
        capatDrX = []
        capatMijX = []
        for i in intersectieSY:
            intersectieSX.append(self.intersectieSusX)
        for i in intersectieJY:
            intersectieJX.append(self.intersectieJosX)
        for i in capatStgY:
            capatStgX.append(self.capatIntervalMinim)
        for i in capatDrY:
            capatDrX.append(self.capatIntervalMaxim)
        for i in capatMijY:
            capatMijX.append(self.mijlocInterval)
        verfunc = np.vectorize(self.f)
        T = verfunc(x)
        self.plot = self.fig2.add_subplot(111)
        self.plot.plot(x, x + self.a * T + self.c)
        self.plot.plot(x, x)
        self.plot.plot(x, x + self.a + self.c)
        self.plot.plot(x, x - self.a + self.c)
        self.plot.plot(intersectieSX, intersectieSY)
        self.plot.plot(intersectieJX, intersectieJY)
        self.plot.plot(capatMijX, capatMijY)
        self.plot.plot(capatDrX, capatDrY)
        self.plot.plot(capatStgX, capatStgY)
        self.plot.grid('--')
        self.plot.legend(
            ['y=x+' + str(self.a) + '*sin(' + str(self.b) + '*x)+' + str(self.c), 'y=x', 'y=x+' + str(self.a + self.c),
             'y=x' + str(-self.a + self.c),
             'x=' + str(self.intersectieSusX), 'x=' + str(self.intersectieJosX), 'x=' + str(self.mijlocInterval),
             'x=' + str(self.capatIntervalMaxim),
             'x=' + str(self.capatIntervalMinim)])

        self.canvas2.draw()

    def gui2(self, ):
        ''' genereaza primii n termeni '''
        self.listView.delete(0, END)
        d = 0
        for i in self.listaCalcule:
            self.listView.insert(END, "x" + str(d) + "= " + str(i))
            d += 1
        self.listView.pack(fill="both", expand=YES)

    def functie2(self, n):
        ''' aici se fac calculele pentru fiecare xi '''
        x0 = self.x0
        self.listaCalcule.append(x0)
        self.listaCalculeGrafic.append(x0)
        for i in range(0, n):
            x1 = self.x0 + self.a * math.sin(self.b * self.x0) + self.c
            self.listaCalcule.append(x1)
            self.x0 = x1
        '''for i in range(0, n):
            x1 = self.x0 + self.a*sin(self.b*self.x0)+self.c
            self.listaCalculeGrafic.append(x1)
            self.x0 = x1'''

    def cautaNumere(self):
        i = 0
        while 2*i*math.pi < self.x0:
            i += 1
        return i

    def genereaza(self):
        self.a = float(self.textA.get())
        self.b = float(self.textB.get())
        self.c = float(self.textC.get())
        x0 = float(self.textX0.get())
        self.x0 = x0
        self.it = int(self.textIt.get())
        i = self.cautaNumere()
        if self.b == 0 or self.a == 0 or self.c > self.a:
            raise ValueError
        self.punctFix = np.arcsin(-self.c / self.a) / self.b
        self.axaSus = "x+" + str(self.a + self.c)
        self.axaJos = "x+" + str(-self.a + self.c)
        self.intersectieSusX = np.arcsin(1) / self.b + 2*(i-1) * math.pi / self.b
        self.intersectieJosX = np.arcsin(-1) / self.b + 2*i * math.pi / self.b
        self.intersectieSusY = self.intersectieSusX + self.a + self.c
        self.intersectieJosY = self.intersectieJosX - self.a + self.c
        self.capatIntervalMinim = self.punctFix + 2*(i-1) * math.pi / self.b
        self.capatIntervalMaxim = self.punctFix + 2*i*math.pi / self.b
        self.mijlocInterval = (-1) * self.punctFix + 2*(i-0.5) * math.pi / self.b
        print("a=" + str(self.a))
        print("b=" + str(self.b))
        print("intersectia cu axele: " + str(self.punctFix) + '+2*k*Pi/' + str(
            self.b) + '\n' + self.axaSus + '\n' + self.axaJos)
        print("inceput interval: " + str(self.capatIntervalMinim))
        print("mijloc interval: " + str(self.mijlocInterval))
        print("sfarsit interval: " + str(self.capatIntervalMaxim))
        print("intersectie sus X: " + str(self.intersectieSusX))
        print("intersectie jos X: " + str(self.intersectieJosX))
        print("intersectie sus Y: " + str(self.intersectieSusY))
        print("intersectie jos Y: " + str(self.intersectieJosY))
        if self.intersectieJosY > self.capatIntervalMinim and self.intersectieSusY < self.capatIntervalMaxim:
            print("se poate")
        else:
            print("nu se poate")

    def iaValori(self):
        self.listaCalcule.clear()
        self.listaCalculeGrafic.clear()
        self.functie2(self.it)
        'root2 = Tk()'
        'root2.title("Rezultate")'
        self.listaRoots.append(self.root)
        self.gui2()
        self.creeazaGrafic()

    def run(self):
        self.root.mainloop()

    def configurare(self):
        self.textX0.config(font="Helvetica 15 bold")
        self.textIt.config(font="Helvetica 15 bold")
        self.textB.config(font="Helvetica 15 bold")
        self.textA.config(font="Helvetica 15 bold")
        self.textC.config(font="Helvetica 15 bold")
        self.labelIt.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.labelX0.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.labelRec.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.labelA.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.labelB.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.labelC.config(font="Helvetica 15 bold", fg="darkblue", pady=25)
        self.buttonCalculeaza.config(font=20, bg="darkblue", fg="white", bd=8)
        self.buttonGenereaza.config(font=20, bg="darkblue", fg="white", bd=8)
        self.buttonUrmator.config(font=20, bg="darkblue", fg="white", bd=8)

    def apasatButonCalculeaza(self):
        try:
            self.genereaza()
            self.iaValori()
            self.plot.plot(10, 7, color="white", marker="x", linestyle="")

            self.canvas2.draw()
        except ValueError:
            self.fig2.clear()
            self.canvas2.draw()
            self.listView.delete(0, END)
            eroare = Tk()
            eroare.title("Eroare")
            cadru = Frame(eroare)
            eticheta = Label(cadru, text="Valori gresite!")
            eticheta.config(font="Helvetica 30 bold", fg="darkblue", pady=50)
            cadru.pack()
            eticheta.pack()
            eroare.lift(self.root)

    def urmator(self):
        '''
        :return:
        '''
        y = self.x0+self.a*math.sin(self.b)+self.c
        capatMijY = np.arange(0.0, y, 0.01)
        intersectieSX=[]
        for i in capatMijY:
            intersectieSX.append(self.x0)
        self.plot.plot(intersectieSX, capatMijY)
        self.canvas2.draw()


    def __init__(self):
        '''Creare'''
        self.incrementeazaGraficRelatieRecurenta = 0
        self.a = 0
        self.x0 = 0
        self.b = 0
        self.c = 0
        self.punctFix = 0
        self.axaSus = ''
        self.axaJos = ''
        self.intersectieSusX = 0
        self.intersectieJosX = 0
        self.intersectieSusY = 0
        self.intersectieJosY = 0
        ''' capatIntervalMinim reprezinta stanga intervalului din desen '''
        self.capatIntervalMinim = 0
        self.capatIntervalMaxim = 0
        self.mijlocInterval = 0
        self.it = 0
        self.listaCalculeGrafic = []
        self.listaCalcule = []
        self.listaRoots = []
        self.root = Tk()
        'self.rootNou = Tk()'
        self.root.title("Functie")
        self.masterFrame = Frame(self.root)
        self.submasterFrameStanga = Frame(self.masterFrame)
        self.submasterFrameDreapta = Frame(self.masterFrame)
        self.frameK = Frame(self.submasterFrameStanga)
        self.topFrame = Frame(self.submasterFrameStanga)
        self.bottomFrame = Frame(self.submasterFrameStanga)
        self.centerFrame = Frame(self.submasterFrameStanga)
        self.anotherFrame = Frame(self.submasterFrameStanga)
        self.frameGenereaza = Frame(self.submasterFrameStanga)
        self.frameC = Frame(self.submasterFrameStanga)
        self.buttonCalculeaza = Button(self.bottomFrame, text="Calculeaza", command=self.apasatButonCalculeaza,
                                       padx=10, pady=5)
        self.buttonGenereaza = Button(self.frameGenereaza, text="Genereaza", command=self.genereaza, padx=10, pady=5)
        self.buttonUrmator = Button(self.submasterFrameDreapta, text="Urmator", command=self.urmator, padx=10, pady=5)
        self.textX0 = Entry(self.anotherFrame)
        self.labelRec = Label(self.topFrame, text="Relatie recurenta:\n1. x_n+1=x_n+a*sin(b*x_n)+c")
        self.labelX0 = Label(self.anotherFrame, text="Introduceti primul termen al sirului:")
        self.labelIt = Label(self.centerFrame, text="Introduceti numarul de termeni de generat:")
        self.textIt = Entry(self.centerFrame)
        self.labelA = Label(self.topFrame, text="Introduceti pe a:")
        self.textA = Entry(self.topFrame)
        self.labelB = Label(self.frameK, text="Introduceti pe b:")
        self.textB = Entry(self.frameK)
        self.textC = Entry(self.frameC)
        self.labelC = Label(self.frameC, text="Introduceti pe c:")
        self.configurare()

        '''Adaugare in ordine'''
        self.masterFrame.pack()
        self.submasterFrameStanga.pack(side=LEFT)
        self.submasterFrameDreapta.pack(side=LEFT)
        self.topFrame.pack()
        self.frameK.pack()
        self.frameC.pack()
        self.frameGenereaza.pack()
        self.anotherFrame.pack()
        self.centerFrame.pack()
        self.bottomFrame.pack()
        self.labelRec.pack()
        self.labelC.pack(side=LEFT)
        self.textC.pack(side=LEFT)
        self.labelA.pack(side=LEFT)
        self.textA.pack(side=LEFT)
        self.labelB.pack(side=LEFT)
        self.textB.pack(side=LEFT)
        self.labelX0.pack(side=LEFT)
        self.textX0.pack(side=LEFT)
        self.labelIt.pack(side=LEFT)
        self.textIt.pack(side=LEFT)
        self.buttonCalculeaza.pack(side=BOTTOM)
        self.buttonUrmator.pack(side=BOTTOM)
        'self.buttonGenereaza.pack()'

        self.scrollbar = Scrollbar(self.submasterFrameStanga, orient="vertical")
        self.listView = Listbox(self.submasterFrameStanga, height=15, width=30, yscrollcommand=self.scrollbar.set)
        self.scrollbar.config(command=self.listView.yview)
        self.listView.config(font=15)
        self.scrollbar.pack(side="right", fill="y")
        self.listView.pack(fill="both", expand=YES)

        self.fig2 = Figure(figsize=(9, 9))
        self.canvas2 = FigureCanvasTkAgg(self.fig2, master=self.submasterFrameDreapta)
        self.plot = 0
        self.canvas2.get_tk_widget().pack()


g = Gui()
g.run()

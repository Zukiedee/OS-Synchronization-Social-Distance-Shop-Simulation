# OS Assignment 2 - Social Distancing Shop
# Zukiswa Lobola
# June 2020

JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= CustomerLocation.class PeopleCounter.class GridBlock.class ShopGrid.class Customer.class Inspector.class ShopView.class SocialDistancingShop.class   
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/socialDistanceShopSampleSolution/*.class

run: 
	java -cp bin socialDistanceShopSampleSolution.SocialDistancingShop $(n) $(l) $(b) $(m)

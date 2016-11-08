CXX = javac
CFLAGS = -Xlint
BIN = "bin"
RELEASE = "release"
main = BFInterpreter


all: $(main)
	@echo "Compilation finished."

$(main): $(main)
	@echo "--- Compiling $@ ..."
	@if [ ! -d $(BIN) ]; then mkdir $(BIN); fi
	@$(CXX) -d $(BIN) $(CFLAGS) $(main).java $^

clean:
	rm -f *.o $(main) *~

jar:
	@if [ ! -d $(RELEASE) ]; then mkdir $(RELEASE); fi
	jar cfe $(RELEASE)/$(main).jar $(main) -C $(BIN) .

archive:
	tar czvf $(main).tar.gz examples/ *.java Makefile

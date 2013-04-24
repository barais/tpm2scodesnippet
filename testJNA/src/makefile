	
libtestJNA.so:     test.o
	    ld -shared -soname $@.1 -o $@.1.0 test.o
	    ln -s ./libprint.so.1.0 libprint.so.1
	    ln -s ./libprint.so.1   libprint.so

	test.o:          test.c
	    gcc -fPIC -c $<

	clean:
	    rm -f *.so* *.o

/**
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MyTest {

	@Test
	public void test() throws Exception {
		InputStream input = null;
		InputStream expectedOutput = null;
		int i = 0;
		do {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream("input" + String.valueOf(i));
			expectedOutput = Thread.currentThread().getContextClassLoader().getResourceAsStream("output" + String.valueOf(i));

			if (input != null) {
				System.out.println("Running case #" + String.valueOf(i) + " input:");
				System.out.println(IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("input" + String.valueOf(i)), "US-ASCII"));
				System.setIn(input);
				if (expectedOutput != null) {
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					PrintStream realOutput = new PrintStream(byteArrayOutputStream);
					PrintStream preservedOut = System.out;
					System.setOut(realOutput);
					long start =System.currentTimeMillis();
					MySolution.main(null);
					long end = System.currentTimeMillis();
					System.setOut(preservedOut);
					System.out.println("Output:");
					System.out.println(byteArrayOutputStream.toString("US-ASCII"));
					System.out.println("Time: " + (end - start));
					assertEquals(IOUtils.toString(expectedOutput, "US-ASCII"), byteArrayOutputStream.toString("US-ASCII"));
				} else {
					MySolution.main(null);
				}
			}
			i++;
		} while (input != null);
	}
}

package Lexical;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Lexer {
  public static enum TokenType {
    // Token types cannot have underscores
    NUMBER("-?[0-9]+"), BINARYOP("[*|/|+|-]"), STRINGFORMAT("%d|%s"),WHITESPACE("[ \t|\f|\r|\n]+"),KEYWORD("int|char|main|include|return|printf|getchar"),STRING("The sum  is|press enter to continue"),HEADERFILE("stdio.h"),ID("x1|x2|[a-z]|[A-Z]"),DELIMITOR("[.|,|;|]|[|(|)|{|}]"),REALOP("[#|<|>|=>|=<|!=|{|}]");
	  
    public final String pattern;

    private TokenType(String pattern) {
      this.pattern = pattern;
    }
  }

  public static class Token {
    public TokenType type;
    public String data;

    public Token(TokenType type, String data) {
      this.type = type;
      this.data = data;
    }

    @Override
    public String toString() {
      return String.format("<%s %s>", type.name(), data);
    }
  }

  public static ArrayList<Token> lex(String input) {
    // The tokens to return
    ArrayList<Token> tokens = new ArrayList<Token>();

    // Lexer logic begins here
    StringBuffer tokenPatternsBuffer = new StringBuffer();
    for (TokenType tokenType : TokenType.values())
      tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
    Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

    // Begin matching tokens
    Matcher matcher = tokenPatterns.matcher(input);
    while (matcher.find()) {
      if (matcher.group(TokenType.NUMBER.name()) != null) {
        tokens.add(new Token(TokenType.NUMBER, matcher.group(TokenType.NUMBER.name())));
        continue;
      } else if (matcher.group(TokenType.BINARYOP.name()) != null) {
        tokens.add(new Token(TokenType.BINARYOP, matcher.group(TokenType.BINARYOP.name())));
        continue;
      } else if (matcher.group(TokenType.KEYWORD.name()) != null) {
          tokens.add(new Token(TokenType.KEYWORD, matcher.group(TokenType.KEYWORD.name())));
          continue;
        }else if (matcher.group(TokenType.REALOP.name()) != null) {
            tokens.add(new Token(TokenType.REALOP, matcher.group(TokenType.REALOP.name())));
            continue;
          }else if (matcher.group(TokenType.STRING.name()) != null) {
              tokens.add(new Token(TokenType.STRING, matcher.group(TokenType.STRING.name())));
              continue;
            } else if (matcher.group(TokenType.ID.name()) != null) {
                tokens.add(new Token(TokenType.ID, matcher.group(TokenType.ID.name())));
                continue;
              } else if (matcher.group(TokenType.HEADERFILE.name()) != null) {
                  tokens.add(new Token(TokenType.HEADERFILE, matcher.group(TokenType.HEADERFILE.name())));
                  continue;
                } else if (matcher.group(TokenType.DELIMITOR.name()) != null) {
                    tokens.add(new Token(TokenType.DELIMITOR, matcher.group(TokenType.DELIMITOR.name())));
                    continue;
                  }else if (matcher.group(TokenType.STRINGFORMAT.name()) != null) {
                      tokens.add(new Token(TokenType.STRINGFORMAT, matcher.group(TokenType.STRINGFORMAT.name())));
                      continue;
                    }  else if (matcher.group(TokenType.WHITESPACE.name()) != null)
        continue;
    }

    return tokens;
  }

  public static void main(String[] args) {
    
    tostring();
  
  }
  
  private static final String FILENAME = "/home/mozzay/Downloads/lexical.c";
  
  public static String tostring() {

		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine = null;

		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				
				ArrayList<Token> tokens = lex(sCurrentLine);
			    for (Token token : tokens)
			      System.out.println(token);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return sCurrentLine;

	}
}


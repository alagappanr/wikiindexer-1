/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 *
 */
public class WikipediaParserTest {

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseSectionTitle(java.lang.String)}.
	 */
	@Test
	public final void testParseSectionTitle() {
		//null test
		assertEquals(null, WikipediaParser.parseSectionTitle(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseSectionTitle(""));
		
		//level 1 test
		assertEquals("section", WikipediaParser.parseSectionTitle("== section =="));
		
		//level 2 test
		assertEquals("section", WikipediaParser.parseSectionTitle("=== section ==="));
		
		//level 3 test
		assertEquals("section", WikipediaParser.parseSectionTitle("==== section ===="));
		
		//level 4 test
		assertEquals("section", WikipediaParser.parseSectionTitle("===== section ====="));
		
		//level 5 test
		assertEquals("section", WikipediaParser.parseSectionTitle("====== section ======"));
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseListItem(java.lang.String)}.
	 */
	@Test
	public final void testParseListItem() {
		//null test
		assertEquals(null, WikipediaParser.parseListItem(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseListItem(""));
		
		//unordered list level 1
		assertEquals("unordered item", WikipediaParser.parseListItem("* unordered item"));
		
		//unordered list level 2
		assertEquals("unordered item", WikipediaParser.parseListItem("** unordered item"));
		
		//unordered list level 4
		assertEquals("unordered item", WikipediaParser.parseListItem("**** unordered item"));
		
		//ordered list level 1
		assertEquals("ordered item", WikipediaParser.parseListItem("# ordered item"));
		
		//ordered lists level 3
		assertEquals("ordered item", WikipediaParser.parseListItem("### ordered item"));
		
		//definition list
		assertEquals("definition item", WikipediaParser.parseListItem(": definition item"));
		
		/* TODO: Add more tests */
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTextFormatting(java.lang.String)}.
	 */
	@Test
	public final void testParseTextFormatting() {
		//null tests
		assertEquals(null, WikipediaParser.parseTextFormatting(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseTextFormatting(""));
		
		//test italics
		assertEquals("This is italicized text test", WikipediaParser.parseTextFormatting("This is ''italicized text'' test"));
		
		//test bold
		assertEquals("This is bold text test", WikipediaParser.parseTextFormatting("This is '''bold text''' test"));
		
		//test both
		assertEquals("This is italics bold test", WikipediaParser.parseTextFormatting("This is '''''italics bold''''' test"));
		
		//test both 2
		assertEquals("This is italics and bold text test", WikipediaParser.parseTextFormatting("This is ''italics'' and '''bold''' text test"));
		
		/* TODO: Add more tests */
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTagFormatting(java.lang.String)}.
	 */
	@Test
	public final void testParseTagFormatting() {
		//null test
		assertEquals(null, WikipediaParser.parseTagFormatting(null));
		
		//empty test
		assertEquals("", WikipediaParser.parseTagFormatting(""));
		
		//empty tag test
		assertEquals("Watch the disappear", WikipediaParser.parseTagFormatting("Watch the <tag/> disappear"));
		
		//tag with content
		assertEquals("I should not vanish", WikipediaParser.parseTagFormatting("<random> I should not vanish </random>"));
		
		//tag with attributes
		assertEquals("Attributes or not, I'm still here", WikipediaParser.parseTagFormatting("<mytag val='some' otherval ='more'> Attributes or not, I'm still here</mytag>"));
		
		//html escaping
		assertEquals("Did you get me right?", WikipediaParser.parseTagFormatting("&lt;painful attr1='yes' attr2='no' &gt;Did you get me right?&lt;/pain&gt;"));
		
		assertEquals("{{tl|cite book}}", WikipediaParser.parseTagFormatting("<nowiki>{{tl|cite book}}</nowiki>"));
		
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseTemplates(java.lang.String)}.
	 */
	@Test
	public final void testParseTemplates() {
		assertEquals("", WikipediaParser.parseTemplates("<ref></ref><ref>{{cite news |title=Украинцы планируют убийства в Грузии? |url=http://www.km.ru/magazin/view.asp?id={5CE15A8F-9F1E-4C36-A007-0C818963B6CD} |work= |publisher=KMnews.RU |date=13 August 2008 |language=Russian |accessdate=4 December 2008 }}</ref>"));
		assertEquals("", WikipediaParser.parseTemplates("{{YouTube|TnIpQhDn4Zg|Russ Conway playing Side Saddle}}"));
		assertEquals("", WikipediaParser.parseTemplates("{{Unreferenced stub|auto=yes|date=December 2009}}"));
		assertEquals("", WikipediaParser.parseTemplates("{{Reflist}}"));
		assertEquals("", WikipediaParser.parseTemplates("{{Persondata |NAME = Kay, Kathie" +
		"|ALTERNATIVE NAMES = Connie Wood" +
		"|SHORT DESCRIPTION = Singer" +
		"|DATE OF BIRTH     = 20 November 1918" +
		"|PLACE OF BIRTH    = [[Gainsborough, Lincolnshire|Gainsborough]], [[Lincolnshire]], [[England]], UK" +
		"|DATE OF DEATH     = 9 March 2005" +
		"|PLACE OF DEATH    = [[Largs]], [[Ayrshire]], [[Scotland]], UK" +
		"}}"));
		assertEquals("", WikipediaParser.parseTemplates("{{for|the American actor|Russ Conway (actor)}}" +
		"{{Use dmy dates|date=November 2012}}" +
		"{{Infobox musical artist &lt;!-- See Wikipedia:WikiProject_Musicians --&gt;" +
		"| name                = Russ Conway" +
		"| birth_date          = {{birth date|1925|09|2|df=y}}" +
		"| birth_place         = [[Bristol]], [[England]], UK" +
		"| death_date          = {{death date and age|2000|11|16|1925|09|02|df=y}}" +
		"| death_place         = [[Eastbourne]], [[Sussex]], England, UK" +
		"| notable_instruments =" + 
		"}}"));
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser#parseLinks(java.lang.String)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testParseLinks() {
		//null and empty checks
		//
		assertEquals(new Object[]{"",""}, WikipediaParser.parseLinks(""));
		//
		assertEquals(new Object[]{"",""}, WikipediaParser.parseLinks(null));
		
		//simple links
		//-1
		assertEquals(new Object[]{"Lone Star State","Texas"}, WikipediaParser.parseLinks("[[Texas|Lone Star State]]"));
		
		//auto capitalization
		//0
		assertEquals(new Object[]{"London has public transport", "Public_transport"}, WikipediaParser.parseLinks("London has [[public transport]]"));
		
		//drop after _ and , automatically
		//1
		assertEquals(new Object[]{"kingdom", "Kingdom_(biology)"}, WikipediaParser.parseLinks("[[kingdom (biology)|]]"));
		assertEquals(new Object[]{"king dom", "Kingdom_(biology)"}, WikipediaParser.parseLinks("[[kingdom (biology)|king dom]]"));
		//assertEquals(new Object[]{"Gold", "Gold_(TV channel)"}, WikipediaParser.parseLinks("[[Gold (TV channel)|Gold]]|"));
		assertEquals(new Object[]{"Redcliffe", "Redcliffe,_Bristol"}, WikipediaParser.parseLinks("[[Redcliffe, Bristol|Redcliffe]]"));
		
		//2
		assertEquals(new Object[]{"Seattle", "Seattle,_Washington"}, WikipediaParser.parseLinks("[[Seattle, Washington|]]"));
		
		//outside namespace, not interested
		//3
		assertEquals(new Object[]{"Village pump", ""}, WikipediaParser.parseLinks("[[Wikipedia:Village pump|]]"));
		//4
		assertEquals(new Object[]{"Manual of Style", ""}, WikipediaParser.parseLinks("[[Wikipedia:Manual of Style (headings)|]]"));
		//5
		assertEquals(new Object[]{"Wiktionary:Hello",""}, WikipediaParser.parseLinks("[[Wiktionary:Hello]]"));
		//6
		assertEquals(new Object[]{"fr:bonjour",""}, WikipediaParser.parseLinks("[[Wiktionary:fr:bonjour|]]"));
		//7
		assertEquals(new Object[]{"Sound",""}, WikipediaParser.parseLinks("[[media:Classical guitar scale.ogg|Sound]]"));
		//8
		assertEquals(new Object[]{"",""}, WikipediaParser.parseLinks("[[File:wiki.png]]"));
		//9
		assertEquals(new Object[]{"Wikipedia encyclopedia",""}, WikipediaParser.parseLinks("[[File:wiki.png|right|Wikipedia encyclopedia]]"));
		//10
		assertEquals(new Object[]{"Wikipedia logo",""}, WikipediaParser.parseLinks("[[File:wiki.png|frame|alt=Puzzle globe|Wikipedia logo]]"));
												
		
		//blending etc.
		//11
		assertEquals(new Object[]{"New York also has public transportation", "Public_transport"}, WikipediaParser.parseLinks("New York also has [[public transport|public transportation]]"));
		//12
		assertEquals(new Object[]{"San Francisco also has public transportation", "Public_transport"}, WikipediaParser.parseLinks("San Francisco also has [[public transport]]ation"));
		//assertEquals(new Object[]{"working men's clubs", "Working_men's_club"}, WikipediaParser.parseLinks("[[working men's club]]s"));
		//13
		assertEquals(new Object[]{"A micro-second", "Micro-"}, WikipediaParser.parseLinks("A [[micro-]]<nowiki />second"));
		//14
		assertEquals(new Object[]{"Wikipedia:Manual of Style#Links",""}, WikipediaParser.parseLinks("[[Wikipedia:Manual of Style#Links|]]"));
		
		//categories: the method should parse 'em but not index
		//15
		assertEquals(new Object[]{"Character sets",""}, WikipediaParser.parseLinks("[[Category:Character sets]]"));
		
		//same as above, but gos in the index not categories
		//16
		assertEquals(new Object[]{"Category:Character sets",""}, WikipediaParser.parseLinks("[[:Category:Character sets]]"));
		
		//language links: parse but dont add to main index
		//17
		assertEquals(new Object[]{"es:Plancton",""}, WikipediaParser.parseLinks("[[es:Plancton]]"));
		assertEquals(new Object[]{"ru:Планктон",""}, WikipediaParser.parseLinks("[[ru:Планктон]]"));
		
		//external links
		//18
		assertEquals(new Object[]{"Wikipedia",""}, WikipediaParser.parseLinks("[http://www.wikipedia.org Wikipedia]"));
		assertEquals(new Object[]{"",""}, WikipediaParser.parseLinks("[http://www.wikipedia.org]"));
	}

}

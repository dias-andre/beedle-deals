package promo.bot.beedle_deals;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import promo.bot.beedle_deals.adapters.in.web.NotificationControllerTest;
import promo.bot.beedle_deals.application.DealApplicationTest;
import promo.bot.beedle_deals.application.GroupApplicationTest;

@Suite
@SelectClasses({DealApplicationTest.class, GroupApplicationTest.class, NotificationControllerTest.class})
class BeedleDealsApplicationTests {
    // RunsAllTests
}

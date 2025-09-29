package org.example
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions

/*************************************************************************************
 * Патерн проєктування "Відвідувач" (Visitor)
 *
 * Це поведінковий патерн, який дозволяє додавати нові операції до цілої ієрархії класів,
 * не змінюючи самі ці класи, а виділяючи операції в окремий клас-відвідувач.
 * Це досягається завдяки техніці подвійної диспетчеризації, коли відвідувач викликає метод,
 * специфічний для типу об'єкта, над яким він працює.
 **************************************************************************************/

interface ReportElement {
    fun <R> accept(visitor: ReportVisitor<R>): R
}

class FixedPriceContract(val costPerYear: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class TimeAndMaterialContract(val costPerHour: Long, val hours: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}

class SupportContract(val costPerMonth: Long): ReportElement {
    override fun <R> accept(visitor: ReportVisitor<R>): R = visitor.visit(this)
}
interface ReportVisitor<out R> {
    fun visit(contract: FixedPriceContract): R
    fun visit(contract: TimeAndMaterialContract): R
    fun visit(contract: SupportContract): R
}

class MonthlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear / 12

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth
}

class YearlyCostReportVisitor: ReportVisitor<Long> {
    override fun visit(contract: FixedPriceContract): Long = contract.costPerYear

    override fun visit(contract: TimeAndMaterialContract): Long = contract.costPerHour * contract.hours

    override fun visit(contract: SupportContract): Long = contract.costPerMonth * 12
}

class VisitorTest {
    @Test
    fun testVisitor() {
        val projectAlpha = FixedPriceContract(10_000)
        val projectBeta= SupportContract(500)
        val projectGamma = TimeAndMaterialContract(150, 10)
        val projectKappa = TimeAndMaterialContract(50, 50)

        val project = arrayListOf(projectAlpha, projectBeta, projectGamma, projectKappa)

        val monthlyCostVisitor = MonthlyCostReportVisitor()
        val monthlyCost = project.sumOf { it.accept(monthlyCostVisitor) }
        println("Monthly cost: $monthlyCost")
        Assertions.assertThat(monthlyCost).isEqualTo(5333)

        val yearlyCostVisitor = YearlyCostReportVisitor()
        val yearlyCost = project.sumOf { it.accept(yearlyCostVisitor ) }
        println("Yearly cost: $yearlyCost")
        Assertions.assertThat(yearlyCost).isEqualTo(20_000)

    }
}
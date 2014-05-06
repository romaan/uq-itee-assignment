namespace ToDoListApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.RKSurveys",
                c => new
                    {
                        id = c.Int(nullable: false, identity: true),
                        name = c.String(),
                        apple = c.Boolean(nullable: false),
                        mango = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.id);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.RKSurveys");
        }
    }
}

data "template_file" "user_data" {
  template = file("cloudinit-config.yaml")
}

resource "aws_instance" "web" {
  count = var.instance_count

  ami = var.ec2_ami
  instance_type = var.ec2_instance

  #subnet_id = aws_subnet.public_subnet.id
  associate_public_ip_address = "true"
  vpc_security_group_ids = [aws_security_group.allow_tls.id]
  user_data = data.template_file.user_data.rendered

  key_name = var.ssh_key_name

  tags = {
    Name = "${var.env}-app-Buges-Killian-${count.index}"
    Environment = var.env
    Groups = "app-Shiro"
    Owner = "killian.buges@ynov.com"
  }
}

resource "aws_key_pair" "sshKeyPair" {
  key_name = var.ssh_key_name

  public_key = file("~/.ssh/id_rsa.pub")

}